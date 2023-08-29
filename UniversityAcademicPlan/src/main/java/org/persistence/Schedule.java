package org.persistence;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Enumerated;
import static javax.persistence.EnumType.STRING;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	public Schedule() {
	}

	@Id
	@Column
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	@Column
	private LocalTime startTime;
	@Column
	private LocalTime endTime;
	@Column
	@Enumerated(STRING)
	private DayOfWeek dayOfWeek;
	@ManyToOne
	private Course course;
	@OneToOne
	private Lecture lecture;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime param) {
		this.startTime = param;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime param) {
		this.endTime = param;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course param) {
		this.course = param;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture param) {
		this.lecture = param;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek param) {
		this.dayOfWeek = param;
	}

}