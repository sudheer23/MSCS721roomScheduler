package sudheer;

import java.util.ArrayList;

public class Room {
	private String name;
	private int capacity;
	private ArrayList<Meeting> meetings;
	private String building;
	private String location;
	
	
	
	
	public Room(String newName, int newCapacity, String building, String location) {
		setName(newName);
		setCapacity(newCapacity);
		setBuilding(building);
		setLocation(location);
		setMeetings(new ArrayList<Meeting>());
	}

	public void addMeeting(Meeting newMeeting) {
		this.getMeetings().add(newMeeting);
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	


	public int getCapacity() {
		return capacity;
	}


	public int setCapacity(int newCapacity) {
	
		
		int target = -1;
		while(target == -1)
 	{
 		try{
 				target = (newCapacity);
 			}
 		catch(NumberFormatException e)
 			{
 			System.out.println("capacity can only be a number");
 			}
 		}
 	return target;
}
	public String getBuilding() {
		return building;
	}


	public void setBuilding(String building) {
		this.building = building;
	}
	
	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}


	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
	}
}
