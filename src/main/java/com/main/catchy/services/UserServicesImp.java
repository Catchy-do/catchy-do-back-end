package com.main.catchy.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.main.catchy.utils.*;
import com.main.catchy.utils.Responce.UARC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.catchy.model.Contact;
import com.main.catchy.model.Images;
import com.main.catchy.model.MentorCompetences;
import com.main.catchy.model.User;
import com.main.catchy.repository.CompetenceRepository;
import com.main.catchy.repository.ContactRepository;
import com.main.catchy.repository.ImagesRepository;
import com.main.catchy.repository.MentorCompetenceRepository;
import com.main.catchy.repository.RoleRepository;
import com.main.catchy.repository.UserRepository;
import com.main.catchy.repository.VilleRepository;
import com.main.catchy.utils.Responce.Response;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServicesImp {
    @Autowired
    PasswordEncoder encoder;
    private final UserRepository userDao;
    private final ContactRepository cntDao;
    private final RoleRepository roleDao;
    private final VilleRepository villeDao;
    private final CompetenceRepository cmpDao;
    private final MentorCompetenceRepository mentorCmpDao;
    private final ImagesRepository imagesDao;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Response<Object> addUser(UserProfile data) {
        try {
            var userExist = userDao.findUserByEmail(data.getEmail());
            if (userExist == null) {


                var role = roleDao.getRoleByName(data.getIsMentor() ? "MENTOR" : "MENTEE");
                var ville = data.getVilleID() != null ? villeDao.findVilleById(data.getVilleID()) : null;
                Double lat = ville != null ? ville.getVilleRegion().getLat() : 0;
                Double lng = ville != null ? ville.getVilleRegion().getLng() : 0;
                var usr = User.builder().accountNonExpired(false).accountNonLocked(false).active("0").connect(false)
                        .credentialsNonExpired(false).enabled(true).email(data.getEmail()).status("Hors ligne")
                        .username(data.getEmail())
                        .roles(Collections.singletonList(role)).password(encoder.encode(data.getPassword())).build();
                userDao.save(usr);

                var contact = Contact.builder().firstName(data.getFirstName()).lastName(data.getLastName()).sexe(data.getSexe()).adresse(data.getAdresse())
                        .email(data.getEmail()).villeContact(ville).isMentor(data.getIsMentor()).notifId(uuidGenerator()).phoneNumber(data.getPhoneNumber()!=null? data.getPhoneNumber():null)
                        .DateOfBirth(data.getDateOfBirth() != null ? data.getDateOfBirth() : null).isConfirmed(data.getIsMentor() ? false : true)
                        .fullName(data.getFirstName() + " " + data.getLastName()).usr(usr).lat(lat).lng(lng).niveau(data.getNiveau()).build();
                cntDao.save(contact);
                if (data.getCompetencesId() != null && !data.getCompetencesId().isEmpty())
                    data.getCompetencesId().stream().forEach(id -> {
                        var cmp = cmpDao.findById(id).get();
                        var mentorComp = MentorCompetences.builder().competenceId(id).contactId(contact.getContactId()).build();
                        mentorCmpDao.save(mentorComp);
                    });
                Response<Object> reps = new Response<Object>();
                reps.setData(usr.getId());
                reps.setStatus("200");
                reps.setResp(new UARC(200), HttpStatus.OK);
                return reps;
            } else {
                Response<Object> reps = new Response<Object>();
                reps.setStatus("403");
                reps.setResp(new UARC(403), HttpStatus.BAD_REQUEST);
                return reps;
            }
        } catch (Exception e) {
            Response<Object> reps = new Response<Object>();
            reps.setStatus("403");
            reps.setResp(new UARC(403), HttpStatus.BAD_REQUEST);
            return reps;
        }
    }

    public Response<Object> UpdateUser(UserProfile data) {
        try {
            User user = userDao.findUserByID(data.getUserId());
            Contact ctr = cntDao.finContactBYUserID(data.getUserId());
            var ville = data.getVilleID() != null ? villeDao.findVilleById(data.getVilleID()) : null;
            Double lat = ville != null ? ville.getVilleRegion().getLat() : 0;
            Double lng = ville != null ? ville.getVilleRegion().getLng() : 0;

            ctr.setLat(lat);
            ctr.setLng(lng);
            ctr.setFirstName(data.getFirstName());
            ctr.setLastName(data.getLastName());
            ctr.setPhoneNumber(data.getPhoneNumber());
            ctr.setEmail(data.getEmail());
            ctr.setVilleContact(ville);
            ctr.setAdresse(data.getAdresse());
            ctr.setAge(data.getAge());
            ctr.setCin(data.getCin());
            ctr.setSexe(data.getSexe());
            ctr.setBiographie(data.getBiographie());
            ctr.setNiveau(data.getNiveau());

            ctr.setDateOfBirth(data.getDateOfBirth());
            ctr.setZipCode(data.getZipCode());
            cntDao.save(ctr);
            // add mentor competence
            if (data.getCompetencesId() != null && !data.getCompetencesId().isEmpty())
                data.getCompetencesId().stream().forEach(id -> {
                    var cmp = cmpDao.findById(id).get();
                    var mentorComp = MentorCompetences.builder().competenceId(id).contactId(ctr.getContactId()).build();
                    mentorCmpDao.save(mentorComp);
                });
            Response<Object> reps = new Response<Object>();
            reps.setData(ctr.getContactId());
            reps.setStatus("200");
            reps.setResp(new UARC(200), HttpStatus.OK);
            return reps;

        } catch (Exception e) {
            Response<Object> reps = new Response<Object>();
            reps.setStatus("403");
            reps.setResp(new UARC(403), HttpStatus.BAD_REQUEST);
            return reps;
        }
    }

    public List<UserProfile> searchMentors(String sexe, List<Long> competenceID){
        List<UserProfile> result = new ArrayList();
        List<Contact> contact = new ArrayList<>();
        if (sexe != null && competenceID != null) {

            contact = cntDao.findMentors(sexe,competenceID);
        } else {
            contact = cntDao.findAll();
        }

        contact.stream().forEach(ctc -> {
            List<MediaBody> media = new ArrayList<>();
            List<CompetenceBody> competences = new ArrayList<>();
            List<MentorCompetences> mentorCompetence = mentorCmpDao.findCompetenceByContactID(ctc.getContactId());
            mentorCompetence.stream().forEach(comp -> {
                var cmp = cmpDao.findById(comp.getCompetenceId()).get();

                competences.add(CompetenceBody.builder().competenceId(cmp.getCompetenceId()).intitule(cmp.getIntitule())
                        .build());
            });
            List<Images> images = imagesDao.findAllUserFile(ctc.getUsr().getId());
            images.stream().forEach(img -> {

                media.add(MediaBody.builder().id(img.getImageId()).intitule(img.getImgName()).url(img.getImgURL())
                        .type(img.getType()).description(img.getDescription()).build());
            });

            var imageURL = !media.isEmpty() ? media.stream().filter(im -> im.getType().equals("PIC")).findFirst().get().getUrl() : null;

            var info = UserProfile.builder().adresse(ctc.getAdresse()).age(ctc.getAge()).cin(ctc.getCin())
                    .imgUrl(imageURL)
                    .countryName(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getPaysRegion().getCapital() : null)
                    .DateOfBirth(ctc.getDateOfBirth()).email(ctc.getEmail()).firstName(ctc.getFirstName()).zipCode(ctc.getZipCode())
                    .lastName(ctc.getLastName()).ville(ctc.getVilleContact() != null ? ctc.getVilleContact().getName() : null)
                    .villeID(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleID() : null).lat(ctc.getLat()).lng(ctc.getLng())
                    .isMentor(ctc.getIsMentor()).notifID(ctc.getNotifId()).phoneNumber(ctc.getPhoneNumber())
                    .region(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getName() : null)
                    .biographie(ctc.getBiographie()).niveau(ctc.getNiveau())
                    .regionID(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getRegionId() : null).sexe(ctc.getSexe())
                    .userId(ctc.getUsr().getId()).competences(competences).media(media).build();


            result.add(info);
        });

        return result;



    }

    public List<UserProfile> getUserList(String type) {

        List<UserProfile> result = new ArrayList();
        List<Contact> contact = new ArrayList<>();

        if (type != null) {
            contact = cntDao.findContactByType(type.equals("Mentor"));
        } else {
            contact = cntDao.findAll();
        }

        contact.stream().forEach(ctc -> {

            List<MediaBody> media = new ArrayList<>();
            List<CompetenceBody> competences = new ArrayList<>();

            List<MentorCompetences> mentorCompetence = mentorCmpDao.findCompetenceByContactID(ctc.getContactId());
            mentorCompetence.stream().forEach(comp -> {
                var cmp = cmpDao.findById(comp.getCompetenceId()).get();

                competences.add(CompetenceBody.builder()
                        .competenceId(cmp.getCompetenceId())
                        .intitule(cmp.getIntitule()).build());
            });

            List<Images> images = imagesDao.findAllUserFile(ctc.getUsr().getId());
            images.stream().forEach(img -> {
                media.add(MediaBody.builder().id(img.getImageId()).intitule(img.getImgName()).url(img.getImgURL())
                        .type(img.getType()).description(img.getDescription()).build());
            });

            var imageURL = !media.isEmpty() ? media.stream().filter(im -> im.getType().equals("PIC")).findFirst().get().getUrl() : null;

            var info = UserProfile.builder().adresse(ctc.getAdresse()).age(ctc.getAge()).cin(ctc.getCin())
                    //.imgUrl(imageURL)
                    .countryName(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getPaysRegion().getCapital() : null)
                    .DateOfBirth(ctc.getDateOfBirth())
                    .email(ctc.getEmail())
                    .firstName(ctc.getFirstName())
                    .zipCode(ctc.getZipCode())
                    .lastName(ctc.getLastName())
                    .ville(ctc.getVilleContact() != null ? ctc.getVilleContact().getName() : null)
                    .villeID(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleID() : null)
                    .lat(ctc.getLat()).lng(ctc.getLng())
                    .isMentor(ctc.getIsMentor()).notifID(ctc.getNotifId()).phoneNumber(ctc.getPhoneNumber())
                    .region(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getName() : null)
                    .biographie(ctc.getBiographie())
                    .niveau(ctc.getNiveau())
                    .regionID(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getRegionId() : null)
                    .sexe(ctc.getSexe())
                    .userId(ctc.getUsr().getId())
                    .competences(competences)
                    .media(media)
                    .isConfirmed(ctc.getIsConfirmed())
                    .active(ctc.getUsr().getActive())
                    .imgUrl(imageURL)
                    .build();


            result.add(info);
        });

        return result;

    }

    public User findUserByUserName(String username) {
        return userDao.findUserByUserName(username);

    }

    public void addlogoutActivity(long id) {
        User user = userDao.findUserByID(id);
        user.setConnect(false);
        user.setStatus("Hors ligne");
        userDao.save(user);
    }

    public boolean existpassword(Long id, String password) {
        logger.info("check password methode started :");
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        User user = userDao.findUserByID(id);
        String oldPAssword = user.getPassword();

        boolean isPasswordMatch = passwordEncoder.matches(password, oldPAssword);

        if (isPasswordMatch) {
            return true;
        } else {

            return false;
        }
    }

    public Object updatePassword(UserBody ursbody) {
        logger.info("udate password methode started :");
        User user = userDao.findUserByID(ursbody.getUserID());
        user.setPassword(passwordEncoders(ursbody.getNewPassword()));
        userDao.save(user);
        Response<Object> respense = new Response<Object>();
        respense.setStatus("200");
        respense.setResp(new UARC(200), HttpStatus.OK);
        logger.info("password changed succusseffully ");
        return respense;
    }

    public String passwordEncoders(String pwd) {
        String encodedPwd = "";
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        encodedPwd = encoder.encode(pwd);
        return encodedPwd;

    }

    public String uuidGenerator() {

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String[] s = randomUUIDString.split("-");
        return s[0];
    }

    public Boolean CheCkPhone(String phoneNumber) {
        User u = userDao.findUserByUserName(phoneNumber);
        if (u != null) {
            return true;
        } else {
            return false;
        }

    }

    public Object updateUserName(long id, String newuser) {
        User user = userDao.findUserByID(id);
        user.setUsername(newuser);
        userDao.save(user);
        UserProfile result = new UserProfile();
        result.setUsername(user.getUsername());
        Response<UserProfile> reps = new Response<UserProfile>();
        reps.setData(result);
        reps.setStatus("200");
        reps.setResp(new UARC(200), HttpStatus.OK);
        logger.info("UserServicesImp::::::methode=  updateUserPhone() =====> execute succssefully");

        return reps;
    }

    public boolean existUserName(Long id, String userName) {
        User u = userDao.findUserByUserName(userName);
        if (u != null && id != u.getId()) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean checkUserName(String username) {
        User u = userDao.findUserByUserName(username);
        if (u != null) {
            return true;
        } else {
            return false;
        }

    }

//    public void valiadeMentor(boolean validate,  Long userId) {
//        User user = userDao.findUserByID(userId);
//        user.setEnabled(true);
//        user.setActive(validate ? "1" : "0");
//        userDao.save(user);
//        Contact ctc = cntDao.finContactBYUserID(userId);
//        ctc.setIsConfirmed(true);
//        cntDao.save(ctc);
//        if (competenceId != null) {
//            competenceId.stream().forEach(id -> {
//                var mentorComp = MentorCompetences.builder().competenceId(id).contactId(ctc.getContactId()).build();
//                mentorCmpDao.save(mentorComp);
//            });
//        }
//
//    }


    public Object valiadeMentor(confirmMentor confirmMentor) {
        logger.info("validate mentor started ");
        var userId = confirmMentor.getUserId();
        var validate = confirmMentor.isValidate();

//        User user = userDao.findUserByID(userId);
//        user.setEnabled(true);
//        user.setActive(validate ? "1" : "0");
//        userDao.save(user);

        try{
            Contact ctc = cntDao.finContactBYUserID(userId);
            User user = userDao.findUserByID(userId);
            user.setEnabled(validate);
            user.setActive(validate ? "1" : "0");
            userDao.save(user);
            ctc.setIsConfirmed(validate);
            cntDao.save(ctc);

            Response<Object> respense = new Response<Object>();
            respense.setStatus("200");
            respense.setResp(new UARC(200), HttpStatus.OK);
            logger.info("user confirmed succusseffully ");
            return respense;
        }
        catch (Exception e) {
            Response<Object> reps = new Response<Object>();
            reps.setStatus("403");
            reps.setResp(new UARC(403), HttpStatus.BAD_REQUEST);
            return reps;
        }

    }





    public UserProfile getUserProfile(Long userID) {
        List<MediaBody> media = new ArrayList<>();
        List<CompetenceBody> competences = new ArrayList<>();

        var ctc = cntDao.finContactBYUserID(userID);

        List<MentorCompetences> mentorCompetence = mentorCmpDao.findCompetenceByContactID(ctc.getContactId());
        mentorCompetence.stream().forEach(comp -> {
            var cmp = cmpDao.findById(comp.getCompetenceId()).get();

            competences.add(
                    CompetenceBody.builder().competenceId(cmp.getCompetenceId()).intitule(cmp.getIntitule()).build());
        });
        List<Images> images = imagesDao.findAllUserFile(ctc.getUsr().getId());
        images.stream().forEach(img -> {

            media.add(MediaBody.builder().id(img.getImageId()).intitule(img.getImgName()).url(img.getImgURL())
                    .type(img.getType()).description(img.getDescription()).build());
        });
        var imageURL = !media.isEmpty() ? media.stream().filter(im -> im.getType().equals("PIC")).findFirst().get().getUrl() : null;
        var role=ctc.getUsr().getRoles().stream().map( r ->r.getName()).collect(Collectors.toList());
        var info = UserProfile.builder().adresse(ctc.getAdresse()).age(ctc.getAge()).cin(ctc.getCin())
                .countryName(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getPaysRegion().getCapital() : null)
                .DateOfBirth(ctc.getDateOfBirth()).email(ctc.getEmail()).firstName(ctc.getFirstName()).zipCode(ctc.getZipCode())
                .lastName(ctc.getLastName()).ville(ctc.getVilleContact() != null ? ctc.getVilleContact().getName() : null)
                .villeID(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleID() : null)
                .lat(ctc.getLat()).lng(ctc.getLng())
                .paysID(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getPaysRegion().getPkPaysID() : null)
                .isMentor(ctc.getIsMentor()).notifID(ctc.getNotifId()).phoneNumber(ctc.getPhoneNumber()).biographie(ctc.getBiographie())
                .region(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getName() : null)
                .regionID(ctc.getVilleContact() != null ? ctc.getVilleContact().getVilleRegion().getRegionId() : null).sexe(ctc.getSexe()).niveau(ctc.getNiveau())
                .userId(ctc.getUsr().getId()).competences(competences).media(media).
                isConfirmed(ctc.getIsConfirmed()).
                role(role)
                .imgUrl(imageURL)
                .build();

        return info;
    }



}
