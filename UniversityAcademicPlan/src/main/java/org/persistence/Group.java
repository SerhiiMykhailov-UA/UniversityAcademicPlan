package org.persistence;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.OneToMany;

@Entity
@Table(name = "Group")
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	public Group() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	@Column
	private String name;
	@OneToMany(mappedBy = "group")
	private List<Student> student;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public List<Student> getStudent() {
	    return student;
	}

	public void setStudent(List<Student> param) {
	    this.student = param;
	}

}