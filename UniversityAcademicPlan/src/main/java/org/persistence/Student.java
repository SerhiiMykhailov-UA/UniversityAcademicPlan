package org.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.MERGE;
import javax.persistence.Column;

@Entity
public class Student extends User implements Serializable {

	private static final long serialVersionUID = 1L;

	public Student() {
	}
	
	@Column
	private String firstName;
	@Column
	private String lastName;
	@ManyToOne(cascade = { PERSIST, MERGE })
	private Group group;
	@ManyToMany
	private List<Course> course;
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String param) {
		this.firstName = param;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String param) {
		this.lastName = param;
	}

	public Group getGroup() {
	    return group;
	}

	public void setGroup(Group param) {
	    this.group = param;
	}

	public List<Course> getCourse() {
	    return course;
	}

	public void setCourse(List<Course> param) {
	    this.course = param;
	}

}