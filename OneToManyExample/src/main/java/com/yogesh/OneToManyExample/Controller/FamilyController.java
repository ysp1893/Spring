package com.yogesh.OneToManyExample.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.OneToManyExample.DTO.ChildsDTO;
import com.yogesh.OneToManyExample.DTO.ParentsDTO;
import com.yogesh.OneToManyExample.DTO.ResponseErrorDTO;
import com.yogesh.OneToManyExample.DTO.ResponseWrapperDTO;
import com.yogesh.OneToManyExample.Model.Childs;
import com.yogesh.OneToManyExample.Model.Parents;
import com.yogesh.OneToManyExample.Repository.IChildRepository;
import com.yogesh.OneToManyExample.Repository.IParentRepository;
import com.yogesh.OneToManyExample.Services.Impl.ChildServicesImpl;
import com.yogesh.OneToManyExample.Services.Impl.ParentServicesImpl;
import com.yogesh.OneToManyExample.Util.MethodsUtil;

@RestController
@RequestMapping(path = "/api/family")
public class FamilyController {

	@Autowired
	ParentServicesImpl parentServicesImpl;

	@Autowired
	ChildServicesImpl childServicesImpl;

	@Autowired
	IParentRepository iParentRepository;

	@Autowired
	IChildRepository iChildRepository;

	@GetMapping(path = "/findAll")
	public ResponseEntity<?> findAllData(HttpServletRequest httpServletRequest) {
		try {
			List<Parents> plist = iParentRepository.findAll();
			return ResponseEntity.ok(new ResponseWrapperDTO("Data fetched", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
					MethodsUtil.isNullOrEmpty(plist) ? null : plist));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getCause().toString()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/child/findAll")
	public ResponseEntity<?> findAllChild(HttpServletRequest httpServletRequest) {
		try {
			List<Childs> plist = iChildRepository.findAll();
			return ResponseEntity.ok(new ResponseWrapperDTO("Data fetched", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
					MethodsUtil.isNullOrEmpty(plist) ? null : plist));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getCause().toString()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/save")
	public ResponseEntity<?> save(@RequestBody Optional<ParentsDTO> parentsDTO, HttpServletRequest httpServletRequest) {
		try {
			if (!parentsDTO.isEmpty()) {
				ParentsDTO parDTO = parentsDTO.get();
				Parents newParent = new Parents();
				newParent.setFname(parDTO.getFname());
				newParent.setMname(parDTO.getMname());
				// Check for ChildDto
				List<ChildsDTO> childDTO = parDTO.getChiList();
				List<Childs> newChildsList = new ArrayList<Childs>();
				if (MethodsUtil.isNullOrEmpty(childDTO)) {
					System.out.println("Childrens not mention");
				} else {
					childDTO.forEach(c -> {
						System.out.println("child: " + c.getName());
						Childs newChild = new Childs();
						newChild.setAge(c.getAge());
						newChild.setGender(c.getGender());
						newChild.setName(c.getName());
						newChild.setParents(newParent);
//						childServicesImpl.save(newChild);
						newChildsList.add(newChild);
					});
				}
				newParent.setChiList(newChildsList);
				parentServicesImpl.save(newParent);
				return ResponseEntity.ok(new ResponseWrapperDTO("Detail Saved", HttpServletResponse.SC_OK,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), newParent));
			} else {
				return new ResponseEntity<Object>(
						new ResponseErrorDTO("Please send a valid data", HttpServletResponse.SC_BAD_REQUEST,
								MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest)),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getCause().toString()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> update(@RequestBody Optional<ParentsDTO> parentsDTO, @PathVariable(value = "id") String id,
			HttpServletRequest httpServletRequest) {
		try {
			Optional<Parents> opParents = iParentRepository.findById(id);
			if (opParents.isEmpty()) {
				return new ResponseEntity<Object>(new ResponseErrorDTO("No data available",
						HttpServletResponse.SC_BAD_REQUEST,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Non of parent is present"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if (!parentsDTO.isEmpty()) {
				// new Data
				ParentsDTO parDTO = parentsDTO.get();
				List<ChildsDTO> cdtol = parDTO.getChiList();

				// OldData
				Parents newParent = opParents.get();
				newParent.setFname(parDTO.getFname());
				newParent.setMname(parDTO.getMname());
				List<Childs> newChildsList = newParent.getChiList();
				cdtol.forEach(c -> {
					System.out.println("new :" + c.getId());
				});
				if (!MethodsUtil.isNullOrEmpty(cdtol)) {
					cdtol.forEach(c1 -> {
						newChildsList.stream().filter(c2 -> c2.getId().equals(c1.getId())).forEach(c2 -> {
							System.out.println("c1 id: " + c1.getId());
							c2.setAge(c1.getAge());
							c2.setGender(c1.getGender());
							c2.setName(c1.getName());
						});

					});
				}
				newParent.setChiList(newChildsList);
				parentServicesImpl.saveAndFlush(newParent);
				return ResponseEntity.ok(new ResponseWrapperDTO("Detail Updated", HttpServletResponse.SC_OK,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), newParent));

			} else {
				return new ResponseEntity<Object>(
						new ResponseErrorDTO("Please send a valid data", HttpServletResponse.SC_BAD_REQUEST,
								MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest)),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getCause().toString()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") String id, HttpServletRequest httpServletRequest) {
		Optional<Parents> opParents = iParentRepository.findById(id);
		if (opParents.isEmpty()) {
			return new ResponseEntity<Object>(new ResponseErrorDTO("No data available",
					HttpServletResponse.SC_BAD_REQUEST,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Non of parent is present"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			parentServicesImpl.delete(opParents.get());
			return ResponseEntity.ok(new ResponseWrapperDTO("Object Deleted", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), null));
		}
	}

}
