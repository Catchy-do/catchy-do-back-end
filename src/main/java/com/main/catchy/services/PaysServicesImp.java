package com.main.catchy.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.main.catchy.utils.Responce.UARC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.main.catchy.model.Pays;
import com.main.catchy.repository.PaysRepository;
import com.main.catchy.utils.Responce.PaysResp;
import com.main.catchy.utils.Responce.Response;

@Service
public class PaysServicesImp {
	@Autowired
    PaysRepository PaysDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Pays getPaysByName(String cca2) {

		return PaysDao.findByCode(cca2);
	}

	public Object getAllPays() {

		List<Pays> ps = PaysDao.findAll();
		if (ps != null) {
			List<PaysResp> result = new ArrayList<PaysResp>();

			for (int i = 0; i < ps.size(); i++) {

				PaysResp c = getPaysInfo(ps.get(i));

				result.add(c);
			}
			Collections.swap(result, 0, 196);
			Collections.swap(result, 1, 37);
			Collections.swap(result, 2, 111);
			Collections.swap(result, 3, 100);
			Response<List<PaysResp>> respense = new Response<List<PaysResp>>();
			respense.setData(result);
			respense.setStatus("200");
			respense.setResp(new UARC(200), HttpStatus.OK);
			logger.info("PaysServicesImp::::::methode=  getAllPays() =====>execute succssefully!");
			return respense;
		} else {
			Response<List<PaysResp>> respense = new Response<List<PaysResp>>();
			respense.setStatus("119");
			respense.setResp(new UARC(119), HttpStatus.OK);
			logger.info("PaysServicesImp::::::methode=  getAllPays() =====>execute succssefully!");
			return respense;
		}
	}

	private PaysResp getPaysInfo(Pays p) {
		PaysResp c = new PaysResp();
		c.setId(Long.toString(p.getPkPaysID()));
		c.setText(p.getNomFr());

		c.setCode(Integer.toString(p.getPhoneCode()));

		return c;
	}

	public void addPays(Pays pays) {
		PaysDao.save(pays);

	}
//

	public Object getCountryCode() {

		List<Pays> ps = PaysDao.findAll();
		if (ps != null) {
			List<PaysResp> result = new ArrayList<PaysResp>();

			for (int i = 0; i < ps.size(); i++) {

				PaysResp c = getPaysPrefix(ps.get(i));

				result.add(c);
			}
			Response<List<PaysResp>> respense = new Response<List<PaysResp>>();
			respense.setData(result);
			respense.setStatus("200");
			respense.setResp(new UARC(200), HttpStatus.OK);
			logger.info("PaysServicesImp::::::methode=  getAllPays() =====>execute succssefully!");
			return respense;
		} else {
			Response<List<PaysResp>> respense = new Response<List<PaysResp>>();
			respense.setStatus("119");
			respense.setResp(new UARC(119), HttpStatus.OK);
			logger.info("PaysServicesImp::::::methode=  getAllPays() =====>execute succssefully!");
			return respense;
		}
	}

	PaysResp getPaysPrefix(Pays p) {
		PaysResp c = new PaysResp();
		c.setId("+" + Integer.toString(p.getPhoneCode()));
		c.setText("+" + Integer.toString(p.getPhoneCode()));
		return c;
	}

}
