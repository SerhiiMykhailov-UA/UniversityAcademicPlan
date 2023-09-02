package ua.foxminded.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor.AnyAnnotation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.MERGE;
import javax.persistence.Column;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Student extends User implements Serializable {

	@Column
	private String firstName;
	@Column
	private String lastName;
	@ManyToOne(cascade = { PERSIST, MERGE })
	private Group group;
	@ManyToMany
	private List<Course> course;
	@Transient
	private static final long serialVersionUID = 1L;
	
}