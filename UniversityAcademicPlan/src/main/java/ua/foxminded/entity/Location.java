package ua.foxminded.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	@Column
	@NonNull
	private String name;
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Course> course;
	
}