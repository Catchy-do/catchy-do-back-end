package com.main.catchy.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.main.catchy.model.Competence;
import com.main.catchy.services.CompetenceSrvImp;
import com.main.catchy.services.ContactServicesImp;
import com.main.catchy.services.PaysServicesImp;
import com.main.catchy.services.RegionServicesImp;
import com.main.catchy.services.UserServicesImp;
import com.main.catchy.utils.Responce.Response;
import com.main.catchy.utils.UserProfile;

import lombok.RequiredArgsConstructor;


@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/public")
public class PubController {
	private final RegionServicesImp regsrv;
	private final ContactServicesImp cntSrv;
	private final UserServicesImp userServices;
	private final PaysServicesImp paysSvc;

	private final CompetenceSrvImp competenceSer;

	@PostMapping("/addUser")
	public Response<Object> addUser(@RequestBody UserProfile ursbody) {

		return userServices.addUser(ursbody);
	}

	@GetMapping("/competencesList")
	public List<Competence> getCompetencesList() {

		return competenceSer.getCompetencesList();

	}

	@GetMapping("/getRegionCoord/{regionID}")
	public Object getRegionCoord(@PathVariable(name = "regionID") long regionID) {

		return regsrv.getRegionCoord(regionID);

	}

	@GetMapping("/getRegion/{capital}")
	public Object getRegion(@PathVariable(name = "capital") String capital) {

		return regsrv.getRegion(capital);

	}

	@GetMapping("/getAllRegionByCapID/{pkPaysID}")
	public Object getAllRegionByCapID(@PathVariable(name = "pkPaysID") long pkPaysID) {

		return regsrv.getAllRegionByCapID(pkPaysID);

	}

	@GetMapping("/getVille/{id}")
	public Object getVille(@PathVariable(name = "id") String id) {

		return regsrv.getVilleByRegion(id);

	}

	@GetMapping("/getAllPays")
	public Object getAllPays() {

		return paysSvc.getAllPays();

	}

}
