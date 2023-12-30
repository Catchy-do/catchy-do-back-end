package com.main.catchy.services;

import java.util.ArrayList;
import java.util.List;

import com.main.catchy.utils.Responce.UARC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.main.catchy.model.Region;
import com.main.catchy.repository.RegionRepository;
import com.main.catchy.utils.CountryBody;
import com.main.catchy.utils.Responce.CountryResp;
import com.main.catchy.utils.RegionBody;
import com.main.catchy.utils.RegionCord;
import com.main.catchy.utils.Responce.Response;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Component
public class RegionServicesImp  {
	@Autowired
    RegionRepository regDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public Object getRegion(String capital) {
		List<Region> regs = regDao.findALlRegionByCapital(capital);
		if (regs != null) {
			List<CountryResp> result = new ArrayList<CountryResp>();

			for (int i = 0; i < regs.size(); i++) {

				CountryResp c = getRegionInfo(regs.get(i));

				result.add(c);
			}
			Response<List<CountryResp>> respense = new Response<List<CountryResp>>();
			respense.setData(result);
			respense.setStatus("200");
			respense.setResp(new UARC(200), HttpStatus.OK);
			logger.info("RegionServicesImp::::::methode=  getRegion() =====>execute succssefully!");
			return respense;
		} else {
			Response<List<CountryResp>> respense = new Response<List<CountryResp>>();
			respense.setStatus("119");
			respense.setResp(new UARC(119), HttpStatus.OK);
			logger.info("RegionServicesImp::::::methode=  getRegion() =====>execute succssefully!");
			return respense;
		}

	}
	
	public Object getALlRegion(String capital) {
		// TODO Auto-generated method stub
		List<Region> regs = regDao.findALlRegionByCapital(capital);
		if (regs != null) {
			List<RegionBody> result = new ArrayList<RegionBody>();

			for (int i = 0; i < regs.size(); i++) {
				RegionBody c=new RegionBody();
				c.setId(regs.get(i).getRegionId());
                c.setName(regs.get(i).getName()); 
				result.add(c);
			}
			Response<List<RegionBody>> respense = new Response<List<RegionBody>>();
			respense.setData(result);
			respense.setStatus("200");
			respense.setResp(new UARC(200), HttpStatus.OK);
			logger.info("RegionServicesImp::::::methode=  getRegion() =====>execute succssefully!");
		    	return respense;
		}
			 else {
					Response<List<RegionBody>> respense = new Response<List<RegionBody>>();
					respense.setStatus("119");
					respense.setResp(new UARC(119), HttpStatus.OK);
					logger.info("RegionServicesImp::::::methode=  getRegion() =====>execute succssefully!");
					return respense;
				}

	}
	private CountryResp getRegionInfo(Region region) {
		CountryResp c = new CountryResp();
		c.setId(Long.toString(region.getRegionId()));
		c.setText(region.getName());


		return c;
	}

	
	public Object getVilleByRegion(String id) {
		long regionID=Long.parseLong(id);
		Region regs = regDao.findRegionByID(regionID);
		if (regs != null) {
		

			
				CountryResp c = getVilleInfo(regs);

			
			Response<CountryResp> respense = new Response<CountryResp>();
			respense.setData(c);
			respense.setStatus("200");
			respense.setResp(new UARC(200), HttpStatus.OK);
			logger.info("RegionServicesImp::::::methode=  getRegion() =====>execute succssefully!");
			return respense;
		} else {
			Response<CountryResp> respense = new Response<>();
			respense.setStatus("119");
			respense.setResp(new UARC(119), HttpStatus.OK);
			logger.info("RegionServicesImp::::::methode=  getRegion() =====>execute succssefully!");
			return respense;
		}

	}
	private CountryResp getVilleInfo(Region region) {
		CountryResp c = new CountryResp();
		c.setId(Long.toString(region.getRegionId()));
		c.setText(region.getName());
	  if(region.getVille()!=null) {
		
		List<CountryBody> villes= new ArrayList<CountryBody>();
		
		for(int i=0;i<region.getVille().size();i++) {
			CountryBody v= new CountryBody();
			v.setId(Long.toString(region.getVille().get(i).getVilleID()));
			v.setText(region.getVille().get(i).getName());
			villes.add(v);	
		}
		c.setChildren(villes);
		
		
	}

		return c;
	}

	
	public Object getRegionCoord(long regionID) {
		RegionCord cord= new RegionCord();
		Region regs = regDao.findRegionByID(regionID);
		if(regs!=null) {
			cord.setLat(regs.getLat());
			cord.setLng(regs.getLng());
			cord.setRegionID(regs.getRegionId());
		}
		
		
		return cord;
	}

	
	public Object getRegionByName(String name) {
		List<RegionCord> result = new ArrayList<RegionCord>();
		List<Region> regs=  regDao.findRegionByName(name);
		if(regs!=null && !regs.isEmpty()) {
			for(int i =0;i<regs.size();i++) {
			RegionCord r= new RegionCord();
			r.setLat(regs.get(i).getLat());
			r.setLng(regs.get(i).getLng());
			r.setName(regs.get(i).getName());
			r.setRegionID(regs.get(i).getRegionId());
			result.add(r);
			}
		}
		return result;
	}
	
	public Object getAllRegionByCapID(long pkPaysID) {
		List<Region> regs = regDao.findALlRegionByPaysID(pkPaysID);
		if (regs != null) {
			List<CountryResp> result = new ArrayList<CountryResp>();

			for (int i = 0; i < regs.size(); i++) {

				CountryResp c = getRegionInfo(regs.get(i));

				result.add(c);
			}
			Response<List<CountryResp>> respense = new Response<List<CountryResp>>();
			respense.setData(result);
			respense.setStatus("200");
			respense.setResp(new UARC(200), HttpStatus.OK);
			logger.info("RegionServicesImp::::::methode=  getRegion() =====>execute succssefully!");
			return respense;
		} else {
			Response<List<CountryResp>> respense = new Response<List<CountryResp>>();
			respense.setStatus("119");
			respense.setResp(new UARC(119), HttpStatus.OK);
			logger.info("RegionServicesImp::::::methode=  getRegion() =====>execute succssefully!");
			return respense;
		}
	}
	
	public Object getAllRegionByCountry(String name) {
		List<Region> regs = regDao.findALlRegionByCountryName(name);
		if (regs != null) {
			List<CountryResp> result = new ArrayList<CountryResp>();

			for (int i = 0; i < regs.size(); i++) {

				CountryResp c = getRegionInfo(regs.get(i));

				result.add(c);
			}
			Response<List<CountryResp>> respense = new Response<List<CountryResp>>();
			respense.setData(result);
			respense.setStatus("200");
			respense.setResp(new UARC(200), HttpStatus.OK);
			logger.info("RegionServicesImp::::::methode=  getAllRegionByCountry() =====>execute succssefully!");
			return respense;
		} else {
			Response<List<CountryResp>> respense = new Response<List<CountryResp>>();
			respense.setStatus("119");
			respense.setResp(new UARC(119), HttpStatus.OK);
			logger.info("RegionServicesImp::::::methode=  getAllRegionByCountry() =====>execute succssefully!");
			return respense;
		}

	}

	
}
