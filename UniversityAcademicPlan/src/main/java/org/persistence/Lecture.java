package org.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;
import static javax.persistence.InheritanceType.JOINED;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Table(name = "Lecture")
public class Lecture implements Serializable {

	private static final long serialVersionUID = 1L;

	public Lecture() {
	}

	@Id
	private long id;
	private String name;
	@Temporal(DATE)
	private Calendar lectureDate;
	@Temporal(TIMESTAMP)
	private Date lectureTime;

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

	public Calendar getDate() {
		return lectureDate;
	}

	public void setDate(Calendar param) {
		this.lectureDate = param;
	}

	public Date getLectureTime() {
		return lectureTime;
	}

	public void setLectureTime(Date param) {
		this.lectureTime = param;
	}

}