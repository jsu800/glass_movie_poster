/*
 * Copyright (c) 2014 Joseph Su
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nbcu.medialabs.model;

import java.util.ArrayList;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Subject implements Comparable<Subject> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Basic
	private String createdByWearerId;
	
	@Basic
	private String name;
	
	@Basic
	private ArrayList<String> faces = new ArrayList<String>();
	
	@Basic
	private ArrayList<String> notes = new ArrayList<String>();
	
	@Transient
	private float match;

	public Subject() {
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public ArrayList<String> getFaces() {
		return faces;
	}

	public void setFaces(ArrayList<String> faces) {
		this.faces = faces;
	}

	public ArrayList<String> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public float getMatch() {
		return match;
	}

	public void setMatch(float match) {
		this.match = match;
	}

	public String getCreatedByWearerId() {
		return createdByWearerId;
	}

	public void setCreatedByWearerId(String createdByWearerId) {
		this.createdByWearerId = createdByWearerId;
	}

	@Override
	public int compareTo(Subject other) {
		if ( null == other ) {
			return -1;
		}
		
		return Float.compare(match, other.match);
	}
}