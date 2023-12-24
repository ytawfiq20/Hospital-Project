package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    
    @Query(value = "select exists(select email from employee where email=:email)"
        , nativeQuery = true)
    public int isEmailExists(String email);

    @Query(value = "select exists(select phone_number from employee where "
    		+ "phone_number=:phone_number)"
        , nativeQuery = true)
    public int isPhoneNumberExists(String phone_number);
    
    @Query(value=" select concat(employee.first_name,' ',employee.last_name)"
    		+ " from employee, doctor where employee.id=:id group by employee.first_name"
    		, nativeQuery = true)
    public List<String> getDoctorsNames(int id);
    
    @Query(value = "select concat(employee.first_name,' ',employee.last_name) "
    		+ "from employee where id=:id", nativeQuery = true)
    public String getDoctorNameById(int id);
    
    @Query(value = "select id, concat(first_name,' ',last_name) "
    		+ "from employee inner join doctor on id=employee_id", nativeQuery = true)
    public List<String> getDoctorsNamesAndIds();
    
    @Query(value = "select id, concat(first_name,' ',last_name) "
    		+ "from employee inner join nurse on id=employee_id", nativeQuery = true)
    public List<String> getNursesNamesAndIds();
    
    @Query(value = "select * from employee where replace(concat(first_name,last_name),' ','')=:name"
    		, nativeQuery = true)
    public List<Employee> getByName(String name);
    
    @Query(value = "select * from employee "
    		+ "where replace(concat(first_name,last_name),' ','')"
    		+ "LIKE CONCAT('%',:name,'%') group by id", nativeQuery = true)
    public List<Employee> getByNameLike(String name);
}
