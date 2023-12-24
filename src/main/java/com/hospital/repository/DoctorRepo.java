package com.hospital.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
	
	@Query(value = "select employee_id from doctor", nativeQuery = true)
	public List<Integer> getDoctorsIds();
	
    @Query(value = "select department.id, department.name,"
    		+ " employee.id , concat(employee.first_name,' ', employee.last_name)"
    		+ " from department inner join doctor on department.id=doctor.department_id"
    		+ " inner join employee on doctor.employee_id=employee.id", nativeQuery = true)
    public List<String> getAll();
    
    @Query(value = "select employee_id from doctor where department_id=:id",
    		nativeQuery = true)
    public List<String> getDoctorsIdsByDepId(int id);
    
    @Query(value = "select * from doctor where department_id=:id", nativeQuery = true)
    public List<Doctor> getAllDoctorsByDepartmentId(int id);
    
    @Query(value = "select doctor.* from doctor inner join department "
    		+ "on doctor.department_id=department.id inner join patient_departments "
    		+ "on department.id=patient_departments.department_id inner join patient "
    		+ "on patient.id=:patient_id group by employee_id"
    		, nativeQuery = true)
    public List<Doctor> getDoctorsForPatientSessionByPatientId(int patient_id);
    
	@Query(value = "select doctor.* from doctor inner join employee "
			+ "on replace(concat(first_name,last_name),' ','')=:name", nativeQuery = true)
	public List<Doctor> getByName(String name);
	
	@Query(value = " select doctor.* from doctor inner join employee "
			+ "on replace(concat(employee.first_name,employee.last_name),' ','')"
			+ "LIKE concat('%',:name,'%') and job_title='Doctor'"
			, nativeQuery = true)
	public List<Doctor> getByNameLike(String name);
    
}


