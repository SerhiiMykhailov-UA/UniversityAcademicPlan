package org.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToOne;

@Entity
@Table(name = "Lecture")
public class Lecture implements Serializable {

	private static final long serialVersionUID = 1L;

	public Lecture() {
	}

	@Id
	private long id;
	@Column
	private String name;
	@ManyToOne
	private Course course;
	@OneToOne(mappedBy = "lecture")
	private Schedule schedule;

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

	public Course getCourse() {
	    return course;
	}

	public void setCourse(Course param) {
	    this.course = param;
	}

	public Schedule getSchedule() {
	    return schedule;
	}

	public void setSchedule(Schedule param) {
	    this.schedule = param;
	}

}