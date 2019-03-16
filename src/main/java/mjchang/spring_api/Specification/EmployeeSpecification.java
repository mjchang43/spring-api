package mjchang.spring_api.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mjchang.spring_api.model.Department;
import mjchang.spring_api.model.Employee;

public class EmployeeSpecification implements Specification<Employee> {

	private Map<String, Object> conditions;
	
	public EmployeeSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();
		if(conditions.containsKey("id")) {
			if(null != conditions.get("id"))
				predicates.add(cb.equal(root.get("id"), conditions.get("id")));
		}
		
		if(conditions.containsKey("name")) {
			if(null != conditions.get("name"))
				predicates.add(cb.like(root.get("name"), "%" + conditions.get("name") + "%"));
		}
		
		if(conditions.containsKey("gender")) {
			if(null != conditions.get("gender"))
				predicates.add(cb.like(root.get("gender"), "%" + conditions.get("gender") + "%"));
		}
		
		if(conditions.containsKey("phone")) {
			if(null != conditions.get("phone"))
				predicates.add(cb.like(root.get("phone"), "%" + conditions.get("phone") + "%"));
		}
		
		if(conditions.containsKey("address")) {
			if(null != conditions.get("address"))
				predicates.add(cb.like(root.get("address"), "%" + conditions.get("address") + "%"));
		}
		
		if(conditions.containsKey("age")) {
			if(null != conditions.get("age"))
				predicates.add(cb.equal(root.get("age"), conditions.get("age")));
		}
		
		if(conditions.containsKey("department")) {
			if(null != conditions.get("department")) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> joinConditions = objectMapper.convertValue(conditions.get("department"), new TypeReference<Map<String, Object>>(){});
				
				Join<Employee,Department> join = root.join("department", JoinType.INNER);
				
				if(joinConditions.containsKey("id")) {
					if(null != joinConditions.get("id"))
						predicates.add(cb.equal(join.get("id"), joinConditions.get("id")));
				}
				
				if(joinConditions.containsKey("name")) {
					if(null != joinConditions.get("name"))
						predicates.add(cb.like(join.get("name"), "%" + joinConditions.get("name") + "%"));
				}
			}	
		}
		
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
