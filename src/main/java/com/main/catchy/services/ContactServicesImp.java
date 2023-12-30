package com.main.catchy.services;

import com.main.catchy.model.Days;
import com.main.catchy.model.SchedTime;
import com.main.catchy.repository.DaysRepository;
import com.main.catchy.repository.SchedTimeRepository;
import com.main.catchy.utils.Responce.ScheTimeResp;
import com.main.catchy.utils.Responce.UARC;
import com.main.catchy.utils.SchedDays;
import com.main.catchy.utils.SchedTimeBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.main.catchy.model.Contact;
import com.main.catchy.model.User;
import com.main.catchy.repository.ContactRepository;
import com.main.catchy.repository.UserRepository;
import com.main.catchy.utils.Responce.Response;
import com.main.catchy.utils.UserProfile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Component
@AllArgsConstructor
public class ContactServicesImp {

    private final ContactRepository cntDao;
    private final UserRepository usrDao;
    private final DaysRepository dayRepo;
    private final SchedTimeRepository schedTimeRepo;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Boolean checkContactPhone(String phoneNumber) {
        Contact c = cntDao.checkIfPhoenExist(phoneNumber);
        if (c != null) {
            return true;
        } else {
            return false;
        }
    }

    public Object getAdmineProfile(String username) {
        User user = usrDao.findUserByUserName(username);
        UserProfile result = new UserProfile();
        try {

            Response<UserProfile> reps = new Response<UserProfile>();
            reps.setData(result);
            reps.setStatus("200");
            reps.setResp(new UARC(200), HttpStatus.OK);
            logger.info("ContactServicesImp::::::methode=  getAdmineProfile() =====> execute succssefully!");
            return reps;
        } catch (Exception e) {
            Response<UserProfile> reps = new Response<UserProfile>();
            reps.setStatus("403");
            reps.setResp(new UARC(403), HttpStatus.BAD_REQUEST);
            logger.info("ContactServicesImp::::::methode=  getAdmineProfile() =====> Bad Request");
            return reps;
        }

    }

    public Response<Object> addSchedTime(SchedTimeBody data) {
        User user = usrDao.findUserByID(data.getUserId());
        try {
            data.getSchedDays().stream()
                    .forEach(day -> {
                        Days d = dayRepo.findByName(day.getName());
                        day.getSchdtime().forEach(time ->{
                           var sched= SchedTime.builder().sTime(time.getStime()).
                                   eTime(time.getEtime()).day(d).contact(user.getContact()).build();
                            schedTimeRepo.save(sched);
                        });

                    });
            Response<Object> reps = new Response<Object>();
            reps.setStatus("200");
            reps.setResp(new UARC(200), HttpStatus.OK);
            logger.info("ContactServicesImp::::::methode=  addSchedTime() =====> execute succssefully!");
            return reps;
        } catch (Exception e) {
            Response<Object> reps = new Response<Object>();
            reps.setStatus("403");
            reps.setResp(new UARC(403), HttpStatus.BAD_REQUEST);
            logger.info("ContactServicesImp::::::methode=  addSchedTime() =====> bad Request");
            return reps;
        }
    }

    public Response<Object> updateSchedTime(SchedTimeBody data) {
        User user = usrDao.findUserByID(data.getUserId());
        var schedList=schedTimeRepo.findSchedTimeByContactId(user.getContact().getContactId());
        try {
            schedTimeRepo.deleteAll(schedList);

            data.getSchedDays().stream()
                    .forEach(day -> {
                        Days d = dayRepo.findByName(day.getName());
                        day.getSchdtime().forEach(time ->{
                            var sched= SchedTime.builder().sTime(time.getStime()).
                                    eTime(time.getEtime()).day(d).contact(user.getContact()).build();
                            schedTimeRepo.save(sched);
                        });

                    });

            Response<Object> reps = new Response<Object>();
            reps.setStatus("200");
            reps.setResp(new UARC(200), HttpStatus.OK);
            logger.info("ContactServicesImp::::::methode=  updateSchedTime() =====> execute succssefully!");
            return reps;
        } catch (Exception e) {
            Response<Object> reps = new Response<Object>();
            reps.setStatus("403");
            reps.setResp(new UARC(403), HttpStatus.BAD_REQUEST);
            logger.info("ContactServicesImp::::::methode=  updateSchedTime() =====> bad Request");
            return reps;
        }
    }

    public Response<Object> getSchedTime(Long userId) {
        List<SchedTimeBody> result= new ArrayList<>();
        List<SchedDays> sDay= new ArrayList<>();
        User user = usrDao.findUserByID(userId);
        var schedTimes=schedTimeRepo.findSchedTimeByContactId(user.getContact().getContactId());
        try {
            var days=dayRepo.findAll();
            for( Days d:days) {
                var schedTime= schedTimes.stream().filter(s ->d.getDayId().equals( s.getDay().getDayId())).collect(Collectors.toList());
                 List<ScheTimeResp> sTimes = new ArrayList<>();
                schedTime.stream().forEach(s ->{
                    sTimes.add(ScheTimeResp.builder().etime(s.getETime()).stime(s.getSTime()).build());
                });
                sDay.add(SchedDays.builder().dayId(d.getDayId()).name(d.getName()).schdtime(sTimes).build());
                result.add(SchedTimeBody.builder().userId(userId).schedDays(sDay).build());
            }


            Response<Object> reps = new Response<Object>();
            reps.setData(result);
            reps.setStatus("200");
            reps.setResp(new UARC(200), HttpStatus.OK);
            logger.info("ContactServicesImp::::::methode=  getSchedTime() =====> execute succssefully!");
            return reps;
        } catch (Exception e) {
            Response<Object> reps = new Response<Object>();
            reps.setStatus("403");
            reps.setResp(new UARC(403), HttpStatus.BAD_REQUEST);
            logger.info("ContactServicesImp::::::methode=  getSchedTime() =====> bad Request");
            return reps;
        }
        }

}
