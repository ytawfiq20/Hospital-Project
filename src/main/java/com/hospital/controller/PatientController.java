package com.hospital.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.ExaminationSession;
import com.hospital.entity.Patient;
import com.hospital.entity.PatientBills;
import com.hospital.entity.PatientDepartments;
import com.hospital.entity.PatientExaminationDate;
import com.hospital.entity.PatientStatus;
import com.hospital.service.DepartmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.EmployeeService;
import com.hospital.service.ExaminationSessionService;
import com.hospital.service.PatientBillsService;
import com.hospital.service.PatientDepartmentService;
import com.hospital.service.PatientExaminationDateService;
import com.hospital.service.PatientService;
import com.hospital.service.PatientStatueService;
import com.hospital.validation.PatientValidation;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	DepartmentService departmentService;
	@Autowired
	DoctorService doctorService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	PatientService patientService;
	@Autowired
	PatientDepartmentService patientDepartmentService;
	@Autowired
	PatientExaminationDateService patientExaminationDateService;
	@Autowired
	PatientStatueService patientStatueService;
	@Autowired
	PatientBillsService patientBillsService;
	@Autowired
	ExaminationSessionService examinationSessionService;
	
	PatientValidation patientValidation = new PatientValidation();
	
	@GetMapping("/onlineBooking")
	public String onlineBookingForm(Model model) {	
		model.addAttribute("departments", departmentService.getAllDepartmrnts());
		return "online-booking";
	}
	
	@PostMapping("/onlineBooking")
	public String saveOnlineBookingInfo(
			@RequestParam String department_id, Model model,
			@ModelAttribute Patient patient
			) {
		
		/*
		 * validate first name
		 * */
		if(!patientValidation.validateName(patient.getFirst_name())) {
			model.addAttribute("Error", "Check your first name");
			model.addAttribute("departments", departmentService.getAllDepartmrnts());
			model.addAttribute("first_name", patient.getFirst_name());
			model.addAttribute("last_name", patient.getLast_name());
			model.addAttribute("email", patient.getEmail());
			model.addAttribute("phone_number", patient.getPhone_number());
			return "online-booking";
		}
		/*
		 * validate last name
		 * */
		if(!patientValidation.validateName(patient.getLast_name())) {
			model.addAttribute("Error", "Check your last name");
			model.addAttribute("departments", departmentService.getAllDepartmrnts());
			model.addAttribute("first_name", patient.getFirst_name());
			model.addAttribute("last_name", patient.getLast_name());
			model.addAttribute("email", patient.getEmail());
			model.addAttribute("phone_number", patient.getPhone_number());
			return "online-booking";
		}
		/*
		 * validate email
		 * */
		if(patient.getEmail().length()>0) {
			if(!patientValidation.validateEmail(patient.getEmail())) {
				model.addAttribute("Error", "Check your email");
				model.addAttribute("departments", departmentService.getAllDepartmrnts());
				model.addAttribute("first_name", patient.getFirst_name());
				model.addAttribute("last_name", patient.getLast_name());
				model.addAttribute("email", patient.getEmail());
				model.addAttribute("phone_number", patient.getPhone_number());
				return "online-booking";
			}
			if(patientService.isEmailExists(patient.getEmail())) {
				model.addAttribute("Error", "Email exists before");
				model.addAttribute("departments", departmentService.getAllDepartmrnts());
				model.addAttribute("first_name", patient.getFirst_name());
				model.addAttribute("last_name", patient.getLast_name());
				model.addAttribute("email", patient.getEmail());
				model.addAttribute("phone_number", patient.getPhone_number());
				return "online-booking";
			}
		}
		/*
		 * validate phone number
		 * */
		if(!patientValidation.validatePhoneNumber(patient.getPhone_number())) {
			model.addAttribute("Error", "Check your phone number");
			model.addAttribute("departments", departmentService.getAllDepartmrnts());
			model.addAttribute("first_name", patient.getFirst_name());
			model.addAttribute("last_name", patient.getLast_name());
			model.addAttribute("email", patient.getEmail());
			model.addAttribute("phone_number", patient.getPhone_number());
			return "online-booking";
		}
		if(patientService.isPhoneNumberExists(patient.getPhone_number())) {
			model.addAttribute("Error", "phone number exists before");
			model.addAttribute("departments", departmentService.getAllDepartmrnts());
			model.addAttribute("first_name", patient.getFirst_name());
			model.addAttribute("last_name", patient.getLast_name());
			model.addAttribute("email", patient.getEmail());
			model.addAttribute("phone_number", patient.getPhone_number());
			return "online-booking";
		}
		
		System.out.println(patient);
		if(department_id.length()==0 || department_id==null) {
			patientService.addPatient(patient);
		}else {
			patientService.addPatient(patient);
			patientDepartmentService.savePatientDepartments(
					new PatientDepartments(
							departmentService.getDepartmentById(
									Integer.parseInt(department_id)), patient));
			model.addAttribute("booked", "Booked Successfully");
		}
		
		return "online-booking";
		
		
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/addPatient")
	public String addPatientForm(Model model) {
		model.addAttribute("departments", departmentService.getAllDepartmrnts());
		return "add-patient";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/addPatient")
	public String addPatient(
			@RequestParam String department_id, Model model,
			@ModelAttribute Patient patient
			) {
		/*
		 * validate first name
		 * */
		if(!patientValidation.validateName(patient.getFirst_name())) {
			model.addAttribute("Error", "Check your first name");
			model.addAttribute("departments", departmentService.getAllDepartmrnts());
			model.addAttribute("first_name", patient.getFirst_name());
			model.addAttribute("last_name", patient.getLast_name());
			model.addAttribute("email", patient.getEmail());
			model.addAttribute("phone_number", patient.getPhone_number());
			return "add-patient";
		}
		/*
		 * validate last name
		 * */
		if(!patientValidation.validateName(patient.getLast_name())) {
			model.addAttribute("Error", "Check your last name");
			model.addAttribute("departments", departmentService.getAllDepartmrnts());
			model.addAttribute("first_name", patient.getFirst_name());
			model.addAttribute("last_name", patient.getLast_name());
			model.addAttribute("email", patient.getEmail());
			model.addAttribute("phone_number", patient.getPhone_number());
			return "add-patient";
		}
		/*
		 * validate email
		 * */
		if(patient.getEmail().length()>0) {
			if(!patientValidation.validateEmail(patient.getEmail())) {
				model.addAttribute("Error", "Check your email");
				model.addAttribute("departments", departmentService.getAllDepartmrnts());
				model.addAttribute("first_name", patient.getFirst_name());
				model.addAttribute("last_name", patient.getLast_name());
				model.addAttribute("email", patient.getEmail());
				model.addAttribute("phone_number", patient.getPhone_number());
				return "add-patient";
			}
			if(patientService.isEmailExists(patient.getEmail())) {
				model.addAttribute("Error", "Email exists before");
				model.addAttribute("departments", departmentService.getAllDepartmrnts());
				model.addAttribute("first_name", patient.getFirst_name());
				model.addAttribute("last_name", patient.getLast_name());
				model.addAttribute("email", patient.getEmail());
				model.addAttribute("phone_number", patient.getPhone_number());
				return "add-patient";
			}
		}
		/*
		 * validate phone number
		 * */
		if(!patientValidation.validatePhoneNumber(patient.getPhone_number())) {
			model.addAttribute("Error", "Check your phone number");
			model.addAttribute("departments", departmentService.getAllDepartmrnts());
			model.addAttribute("first_name", patient.getFirst_name());
			model.addAttribute("last_name", patient.getLast_name());
			model.addAttribute("email", patient.getEmail());
			model.addAttribute("phone_number", patient.getPhone_number());
			return "add-patient";
		}
		if(patientService.isPhoneNumberExists(patient.getPhone_number())) {
			model.addAttribute("Error", "phone number exists before");
			model.addAttribute("departments", departmentService.getAllDepartmrnts());
			model.addAttribute("first_name", patient.getFirst_name());
			model.addAttribute("last_name", patient.getLast_name());
			model.addAttribute("email", patient.getEmail());
			model.addAttribute("phone_number", patient.getPhone_number());
			return "add-patient";
		}
		
		System.out.println(patient);
		
		patientService.addPatient(patient);
		patientDepartmentService.savePatientDepartments(
				new PatientDepartments(
						departmentService.getDepartmentById(
								Integer.parseInt(department_id)), patient));
		model.addAttribute("booked", "Booked Successfully");
		return "redirect:/patient/allPatients";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/allPatients")
	public String getAllPatients(Model model) {
		model.addAttribute("patients", patientService.getAllPatients());
		return "all-patients";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/updatePatient/{id}")
	public String updatePatientForm(@PathVariable int id, Model model) {
		model.addAttribute("patient", patientService.getPatientById(id));
		return "update-patient";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/updatePatient")
	public String saveUpdatesToPatient(@ModelAttribute Patient patient, Model model) {
		
		Patient oldPatient = patientService.getPatientById(patient.getId());
		/*
		 * validate first name
		 * */
		if(!patientValidation.validateName(patient.getFirst_name())) {
			model.addAttribute("Error", "Check your first name");
			model.addAttribute("patient", patientService.getPatientById(patient.getId()));
			return "update-patient";
		}
		/*
		 * validate last name
		 * */
		if(!patientValidation.validateName(patient.getLast_name())) {
			model.addAttribute("Error", "Check your last name");
			model.addAttribute("patient", patientService.getPatientById(patient.getId()));
			return "update-patient";
		}
		/*
		 * validate email
		 * */
		if(patient.getEmail().length()>0) {
			if(!patientValidation.validateEmail(patient.getEmail())) {
				model.addAttribute("Error", "Check your email");
				model.addAttribute("patient", patientService.getPatientById(patient.getId()));
				return "update-patient";
			}
			if(patientService.isEmailExists(patient.getEmail())
					&&!patient.getEmail().equals(oldPatient.getEmail())) {
				model.addAttribute("Error", "Email exists before");
				model.addAttribute("patient", patientService.getPatientById(patient.getId()));
				return "update-patient";
			}
		}
		/*
		 * validate phone number
		 * */
		if(!patientValidation.validatePhoneNumber(patient.getPhone_number())) {
			model.addAttribute("Error", "Check your phone number");
			model.addAttribute("patient", patientService.getPatientById(patient.getId()));
			return "update-patient";
		}
		if(patientService.isPhoneNumberExists(patient.getPhone_number())
				&&!patient.getPhone_number().equals(oldPatient.getPhone_number())) {
			model.addAttribute("Error", "phone number exists before");
			model.addAttribute("patient", patientService.getPatientById(patient.getId()));
			return "update-patient";
		}
		
		patientService.updatePatient(patient);
		return "redirect:/patient/allPatients";
	}
	
	@Secured({"ADMIN"})
	@GetMapping("/deletePatient/{id}")
	public String deletePatient(@PathVariable int id) {
		patientService.deletePatientById(id);
		return "redirect:/patient/allPatients";
	}
	
	/*
	 * 
	 * deal with patient departments
	 * 
	 * 
	 * */
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/getPatientDepartments/{id}")
	public String getPatientDepartmentsById(Model model, @PathVariable int id) {
		List<String> depsIds=
				patientDepartmentService.getDepartmentsIdsByPatientId(id);
		List<Department> departments = new ArrayList<>();
		for(int i=0; i<depsIds.size(); i++) {
			departments.add(departmentService
					.getDepartmentById(Integer.parseInt(depsIds.get(i))));
		}
		model.addAttribute("patient", patientService.getPatientById(id));
		model.addAttribute("departments", departments);
		return "all-patient-departments";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/bookNewDepartment/{id}")
	public String bookNewDepartmentForExistingPatient(@PathVariable int id, Model model) {
		model.addAttribute("patient_id", id);
		model.addAttribute("departments", departmentService.getAllDepartmrnts());
		return "add-patient-department";	
	}
	
	@PostMapping("/bookNewDepartment")
	public String savePatientDepartments(
			@RequestParam String department_id, @RequestParam int patient_id) {
		patientDepartmentService.savePatientDepartments(new PatientDepartments(
				departmentService.getDepartmentById(Integer.parseInt(department_id)),
				patientService.getPatientById(patient_id)
				));
		return "redirect:/patient/getPatientDepartments/"+patient_id;
	}
	/*
	 * 
	 * 
	 * deal with patient examination dates
	 * 
	 * 
	 * */
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/addExaminationDate/{id}")
	public String addExaminationDateForm(Model model, @PathVariable int id) {
		model.addAttribute("patient_id", id);
		return "add-examination-date";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/addExaminationDate")
	public String saveExaminationDate(
			@ModelAttribute PatientExaminationDate examinationDate, 
			@RequestParam int patient_id, Model model) {
		
		System.out.println(patient_id);
		if(examinationDate.getDate().length()==0) {
			model.addAttribute("Error", "Choose Examination Date");
			model.addAttribute("patient_id", patient_id);
			return "add-examination-date";
		}
		patientExaminationDateService.saveExaminationDate(
				new PatientExaminationDate(examinationDate.getDate(), 
						patientService.getPatientById(patient_id))
				);
		return "redirect:/patient/getExaminationDates/"+patient_id;
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/updateExaminationDate/{id}")
	public String updatePatientExaminationDateForm(@PathVariable int id, Model model) {
		model.addAttribute("date", patientExaminationDateService.getExaminationDateById(id));
		return "update-patient-examination-date";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/updateExaminationDate")
	public String saveUpdateToExaminationDate(
			@ModelAttribute PatientExaminationDate patientExaminationDate, Model model) {
		System.out.println(patientExaminationDate);
		int patient_id = patientExaminationDateService
				.getPatientIdByExaminationDateId(patientExaminationDate.getId());
		System.out.println(patient_id);
		if(patientExaminationDate.getDate().length()==0) {
			model.addAttribute("Error", "Choose examination date");
			model.addAttribute("date", patientExaminationDateService
					.getExaminationDateById(patientExaminationDate.getId()));
			return "update-patient-examination-date";
		}
		PatientExaminationDate newPatientExaminationDate = new PatientExaminationDate
				(patientExaminationDate.getId(), patientExaminationDate.getDate(), 
						patientService.getPatientById(patient_id));
		System.out.println(newPatientExaminationDate);
		patientExaminationDateService.updateExaminationDate(newPatientExaminationDate);
		return "redirect:/patient/getExaminationDates/"+patient_id;
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/getExaminationDates/{patient_id}")
	public String getAllExaminationDatesForPatientByPatientId(
			@PathVariable int patient_id, Model model) {
		List<String> examinationDatesAndIds = patientExaminationDateService
				.getPatientDatesAndExaminationIdsByPatientId(patient_id);
		Map<Integer, String> map = new HashMap<>();
		for(int i=0; i<examinationDatesAndIds.size(); i++) {
			String[] split = examinationDatesAndIds.get(i).split(",");
			map.put(Integer.parseInt(split[0]), split[1]);
		}
		model.addAttribute("dates", map);
		model.addAttribute("patient", patientService.getPatientById(patient_id));
		return "all-patient-examination-dates";
	}
	
	@Secured({"ADMIN"})
	@GetMapping("/deleteExaminationDate/{id}")
	public String deleteExaminationDateById(@PathVariable int id, Model model) {
		int patient_id= patientExaminationDateService.getPatientIdByExaminationDateId(id);
		patientExaminationDateService.deleteDateById(id);
		return "redirect:/patient/getExaminationDates/"+patient_id;
	}
	

	/*
	 * 
	 * 
	 * deal with patient status
	 * 
	 * 
	 * */
	 @Secured({"ADMIN","NURSE"})
	 @GetMapping("/addStatus/{id}")
	 public String addConditionForm(@PathVariable int id, Model model) {
		 model.addAttribute("patient_id", id);
		 return "add-patient-status";
	 }
	 
	 @Secured({"ADMIN","NURSE"})
	 @PostMapping("/addStatus")
	 public String saveConditionToPatient(@ModelAttribute PatientStatus patientStatus,
			 Model model, @RequestParam int patient_id) {
		 /*
		  * validate patient status
		  * */
		 if(patientStatus.getState().length()==0) {
			 model.addAttribute("Error", "Enter patient status");
			 model.addAttribute("patient_id", patient_id);
			 model.addAttribute("medicine", patientStatus.getMedicine());
			 model.addAttribute("state", patientStatus.getState());
			 model.addAttribute("re_examination_date", patientStatus.getRe_examination_date());
			 return "add-patient-status";
		 }
		 patientStatueService.addStatus(
				 new PatientStatus(patientStatus.getState(),
						 patientStatus.getMedicine(), patientStatus.getRe_examination_date()
						 , patientService.getPatientById(patient_id))
				 );
		 return "redirect:/patient/getAllStatus/"+patient_id;
	 }
	 
	 @Secured({"ADMIN","NURSE"})
	 @GetMapping("/getAllStatus/{id}")
	 public String getAllStatusForPatientById(@PathVariable int id, Model model) {
		 model.addAttribute("status", 
				 patientStatueService.getAllPatientStatusByPatientId(id));
		 model.addAttribute("patient", patientService.getPatientById(id));
		 model.addAttribute("dates", 
				 patientExaminationDateService.getPatientDatesById(id));
		 return "all-patient-status";
	 }
	 
	 @Secured({"ADMIN","NURSE"})
	 @GetMapping("/updateStatus/{id}")
	 public String updateStatusForm(@PathVariable int id, Model model) {
		 model.addAttribute("status", patientStatueService.getById(id));
		 return "update-patient-status";
	 }
	 
	 @Secured({"ADMIN","NURSE"})
	 @PostMapping("/updateStatus")
	 public String saveUpdatesToStatus(@ModelAttribute PatientStatus patientStatus
			 , Model model) {
		 /*
		  * validate patient status
		  * */
		 int patient_id = patientStatueService.getPatientIdByStatusId(patientStatus.getId());
		 if(patientStatus.getState().length()==0) {
			 model.addAttribute("Error", "Enter patient status");
			 model.addAttribute("patient_id", patient_id);
			 model.addAttribute("medicine", patientStatus.getMedicine());
			 model.addAttribute("state", patientStatus.getState());
			 model.addAttribute("re_examination_date", patientStatus.getRe_examination_date());
			 return "add-patient-status";
		 }
		 PatientStatus newPatientStatus = new PatientStatus(
				 patientStatus.getId(), patientStatus.getState()
				 , patientStatus.getMedicine(), patientStatus.getRe_examination_date()
				 , patientService.getPatientById(patient_id));
		 patientStatueService.update(newPatientStatus);
		 return "redirect:/patient/getAllStatus/"+patient_id;
	 }
	 
	 @Secured({"ADMIN"})
	 @GetMapping("/deleteStatus/{id}")
	 public String deleteStatus(@PathVariable int id) {
		 int patient_id = patientStatueService.getPatientIdByStatusId(id);
		 patientStatueService.deleteStatusById(id);
		 return "redirect:/patient/getAllStatus/"+patient_id;
	 }
	 
	 /*
	  * 
	  * 
	  * Patient Sessions
	  * 
	  * 
	  * */
	 
	 @Secured({"ADMIN","NURSE"})
	 @GetMapping("/addSession/{patient_id}")
	 public String addSessionForm(@PathVariable int patient_id, Model model) {
//		 List<String> patientDepartments= 
//				 patientDepartmentService.getDepartmentsIdsByPatientId(patient_id);
//		 List<List<Doctor>> doctorsInfo = new ArrayList<>();
//		 
//		 for(int i=0; i<patientDepartments.size(); i++) {
//			 int dep_id = Integer.parseInt(patientDepartments.get(i));
//			 doctorsInfo.addAll(Arrays.asList(doctorService.getAllDoctorsByDepartmentId(dep_id)));
//		 }
//		 
//		 Set<Doctor> doctors = new HashSet<>();
//		 
//		 for(int i=0; i<doctorsInfo.size(); i++) {
//			 for(int j=0; j<doctorsInfo.get(i).size(); j++) {
//				 int doc_id= doctorsInfo.get(i).get(j).getId();
//				 doctors.add(doctorService.getDoctorById(doc_id));
//			 }
//		 }
		 List<Doctor> doctors = doctorService
				 .getDoctorsForPatientSessionByPatientId(patient_id);
		 System.out.println(doctors);
		 model.addAttribute("patient_id", patient_id);
		 model.addAttribute("doctors", doctors);
		 return "add-patient-session";
	 }
	 
	 @Secured({"ADMIN","NURSE"})
	 @PostMapping("/addSession")
	 public String saveExaminationSession(@RequestParam int patient_id
			 , @RequestParam String doctor_id, Model model) {
		 int doc_id = Integer.parseInt(doctor_id);
		 examinationSessionService.addExaminationSession(
				 new ExaminationSession(
				 patientService.getPatientById(patient_id),
				 doctorService.getDoctorById(doc_id)
				 ));
		 return "redirect:/patient/getAllSessions/"+patient_id;
	 }
	 
	 @Secured({"ADMIN","NURSE"})
	 @GetMapping("/getAllSessions/{patient_id}")
	 public String getAllPatientSessions(@PathVariable int patient_id, Model model) {
		 model.addAttribute("doctors", examinationSessionService
				 .getDoctorsIdsByPatientId(patient_id));
		 List<ExaminationSession> allByPatientId = examinationSessionService
				 .getAllByPatientId(patient_id);
		 
		 model.addAttribute("patient", patientService.getPatientById(patient_id));
		 model.addAttribute("sessions", allByPatientId);
		 return "all-patient-examination-sessions";
	 }
	 
	 @Secured({"ADMIN","NURSE"})
	 @GetMapping("/updateSession/{session_id}")
	 public String updateSessionForm(@PathVariable int session_id, Model model) {
		 ExaminationSession session = examinationSessionService
				 .getExaminationSessionById(session_id);
		 model.addAttribute("patient_id", session.getPatient().getId());
		 model.addAttribute("session_id", session_id);
		 System.out.println(session.getPatient().getId());
		 List<Doctor> doctors = doctorService
				 .getDoctorsForPatientSessionByPatientId(session.getPatient().getId());
		 System.out.println(doctors);
		 model.addAttribute("doctors", doctors);
		 return "update-examination-session";
	 }
	 
	 @Secured({"ADMIN","NURSE"})
	 @PostMapping("/updateSession")
	 public String saveUpdatesToSession(@RequestParam int patient_id
			 , @RequestParam String doctor_id, @RequestParam int session_id) {
		 int doc_id = Integer.parseInt(doctor_id);
		 ExaminationSession newExaminationSession = new ExaminationSession(
				 session_id, patientService.getPatientById(patient_id),
				 doctorService.getDoctorById(doc_id)
				 );
		 examinationSessionService.updateExaminationSession(newExaminationSession);
		 return "redirect:/patient/getAllSessions/"+patient_id;
	 }
	 
	 @Secured({"ADMIN"})
	 @GetMapping("/deleteSession/{session_id}")
	 public String deleteSession(@PathVariable int session_id) {
		 int patient_id = examinationSessionService.getExaminationSessionById(session_id)
				 .getPatient().getId();
		 examinationSessionService.deleteExaminationSessionById(session_id);
		 return "redirect:/patient/getAllSessions/"+patient_id;
	 }
	 
	 /*
	  * 
	  * 
	  * deal with patient bills
	  * 
	  * 
	  * */
	 
	 @Secured({"ADMIN","NURSE"})
	 @GetMapping("/getBill/{patient_id}")
	 public String getBillForm(@PathVariable int patient_id, Model model) {
		 model.addAttribute("patient", patientService.getPatientById(patient_id));
		 List<Doctor> doctors = doctorService.getAllDoctors();
//		 Map<String, String> map = new HashMap<>();
//		 for(int i=0; i<doctors.size(); i++) {
//			 String[] doctor = doctors.get(i).split(",");
//			 map.put(doctor[0], doctor[1]);
//		 }
		 model.addAttribute("doctors", doctors);
		 return "patient-bill";
	 }
	 
	 @Secured({"ADMIN","NURSE"})
	 @PostMapping("/getBill")
	 public String saveBill(@RequestParam int id
			 ,@RequestParam String doctor_id, Model model) {
		 int price = doctorService
				 .getDoctorById(Integer.parseInt(doctor_id)).getExamination_price();
		 PatientBills bill = new PatientBills(price, (short) 1
				 , patientService.getPatientById(id));
		 patientBillsService.addBill(bill);
		 
		 model.addAttribute("bill", bill);
		 model.addAttribute("doctor", doctorService.getDoctorById(Integer.parseInt(doctor_id)));
		 return "view-bill-info";
		 
	 }
	
	 /*
	  * search
	  * */
	 @Secured({"ADMIN","NURSE"})
	 @GetMapping("/search")
	 public String searchByName(@RequestParam String name, Model model) {
		 String name1 = removeSpaces(name);
		 System.out.println(name1);
		 model.addAttribute("patients1", patientService.getByName(name1));
		 model.addAttribute("patients", patientService.getAllPatients());
		 return "all-patients";
	 }
	 
	 public String removeSpaces(String name) {
		 String ans = "";
		 for(int i=0; i<name.length(); i++) {
			 if(name.charAt(i)!=' ') {
				 ans += name.charAt(i);
			 }
		 }
		 return ans;
	 }
	
}
