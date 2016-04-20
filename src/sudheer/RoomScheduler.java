package sudheer;
 

import java.io.FileInputStream;



import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * class to handle rooms and meeting and scheduling 
 * @author sudheer mandava
 * 
 */
  



public class RoomScheduler {
	// keyboard reader
	protected static Scanner keyboard = new Scanner(System.in);
	protected static final Logger logger = Logger.getLogger("RoomScheduler.class");
// main function to run room scheduler 
	public static void main(String[] args) {
		initializeLogging();
		// loop until end occurs 
		Boolean end = false;
		ArrayList<Room> rooms = new ArrayList<Room>();
	
		try {
			while (!end) {
				switch (mainMenu()) {

				case 1:
					logger.info(addRoom(rooms));
					break;
				case 2:
					logger.info(removeRoom(rooms));
					break;
				case 3:
					logger.info(scheduleRoom(rooms));
					break;
				case 4:
					logger.info(listSchedule(rooms));
					break;
				case 5:
					logger.info(listRooms(rooms));
					break;
				case 6 :
					exportRooms(rooms);
				break;
				case 7 :
					 			end = true;
					 			break;
					
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Print Number Only ");
			System.exit(0);
		}
	}
	private static void initializeLogging() {
		Properties pro = new Properties();
		try {
			FileInputStream finput = new FileInputStream(
					"configuration/log4j.properties");
			pro.load(finput);
			PropertyConfigurator.configure(pro);
		} catch (FileNotFoundException e) {
			logger.info("Unable to locate the logging properties file");
		} catch (IOException e) {
			logger.error("Error while reading the logging file");
		}

	}
	
	
	private static void exportRooms(ArrayList<Room> roomList) throws JSONException 
	{
		
		
		JSONArray roomObj = new JSONArray();
		JSONObject singleObj = new JSONObject();
			for (Room room : roomList) 
				{
			
					singleObj.put("roomName", room.getName());
					singleObj.put("capacity", room.getCapacity());
		
	 
					 for (Meeting m : room.getMeetings()) 
					 {
									 	
							singleObj.put("start", m.getStartTime().toString());
							singleObj.put("stop", m.getStopTime().toString());
					 
					 }	

						roomObj.put(singleObj);	
				}
			 
			
			logger.info("Successfully Copied JSON Object to File...");
		
			FileWriter file;
			try {
				file = new FileWriter("rooms.json");
				file.write(roomObj.toString());
				System.out.println("Successfully Copied JSON Object to File...");
				System.out.println("\nJSON Object: " + roomObj);
				file.flush();
				file.close();
			}
			
			
		          catch (IOException e1)
			{
				
				e1.printStackTrace();
			} 
				
		
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private static void exportSchedule(ArrayList<Room> roomList) throws JSONException {
		
		 
		
		
		JSONArray scheduleObj = new JSONArray();
		JSONObject singleRoomObj = new JSONObject();
		for (Room room : roomList) 
		{
			
			
			JSONArray meetingArrayObj = new JSONArray();
		 for (Meeting m : room.getMeetings()) 
		 {
			 	JSONObject singleObj = new JSONObject();
			 	
				singleObj.put("start", m.getStartTime().toString());
				singleObj.put("stop", m.getStopTime().toString());
				//System.out.println(singleObj);
				((List<Room>) meetingArrayObj).addAll((Collection<? extends Room>) singleObj);
		 }
		 
		
		 
		}
		
		((List<Room>) scheduleObj).addAll((Collection<? extends Room>) singleRoomObj);
		
		FileWriter file;
		try {
			file = new FileWriter("schedule.json");
			file.write(scheduleObj.toString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + scheduleObj);
			file.flush();
			file.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	protected static String listSchedule(ArrayList<Room> roomList) {
		String roomName = getRoomName();
		System.out.println(roomName + " Schedule");
		System.out.println("---------------------");

		for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) {
			System.out.println(m.toString());
		}

		return "";
	}


	
	protected static int mainMenu() {
		System.out.println("Main Menu:");
		System.out.println("  1 - Add a room");
		System.out.println("  2 - Remove a room");
		System.out.println("  3 - Schedule a room");
		System.out.println("  4 - List Schedule");
		System.out.println("  5 - List Rooms");
		System.out.println("6 - export rooms");
		
		System.out.println("Enter your selection: ");

	

	 	
			int target = -1;
			while(target == -1)
	 	{
	 		try{
	 				target = Integer.parseInt(keyboard.next());
	 			}
	 		catch(NumberFormatException e)
	 			{
	 			System.out.println("ERROR: Please input an integer from 0-6");
	 			}
	 		}
	 	return target;
	}
	
	protected static String addRoom(ArrayList<Room> roomList) {
		System.out.println("Add a room:");
		String name = getRoomName();
		System.out.println("Room capacity?");
		int capacity = keyboard.nextInt();
	System.out.println("building name?");
	String newBuilding = keyboard.next();
	System.out.println("location ?");
	String newLocation = keyboard.next();
		try {
			

			Room newRoom = new Room(name, capacity, newBuilding , newLocation);
			roomList.add(newRoom);

			return "Room '" + newRoom.getName() + "' added successfully!";
		} catch (Exception e) {
			return "Room " + "not" + "added";
		}

	}

	public static String removeRoom(ArrayList<Room> roomList) {
		 		
		 		
		 		System.out.println("Remove a room:");

				int index = findRoomIndex(roomList, getRoomName());

				if (index != 0) {
					roomList.remove(index);
					return "Room removed successfully";
				} else {
					return "Room doesn't exist!";
				}
		 	}
	public static String listRooms(ArrayList<Room> roomList) {
		System.out.println("Room Name - Capacity");
		System.out.println("---------------------");

		for (Room room : roomList) {
			System.out.println(room.getName() + " - " + room.getCapacity());
		}

		System.out.println("---------------------");

		return roomList.size() + " Room(s)";
	}

	protected static String scheduleRoom(ArrayList<Room> Roomlist) {
		System.out.println("Schedule a room:");
		String name = getRoomName();
		for (int index = 0; index < Roomlist.size(); index++)

			if (Roomlist.get(index).toString().compareTo(name) == 1) {
				Roomlist.get(index);
				System.out.println("Already Exists");

			} else {
				System.out.println("Start Date? (yyyy-mm-dd hh:mm:ss.fffffffff):");
				//String startDate = keyboard.next();
				System.out.println("Start Time?");
				String startTime = keyboard.next();
				startTime = startTime + ":00.0";

				System.out.println("End Date? (yyyy-mm-dd hh:mm:ss.fffffffff):");
				//String endDate = keyboard.next();
				System.out.println("End Time?");
				String endTime = keyboard.next();
				endTime = endTime + ":00.0";

				//Timestamp startTimestamp = Timestamp.valueOf(startDate + " " + startTime);
			//	Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);

				Timestamp startTimestamp = Timestamp.valueOf("2016-02-10 10:00:00");
				Timestamp endTimestamp = Timestamp.valueOf("2016-02-11 10:00:00");
				System.out.println("Subject?");
				String subject = keyboard.nextLine();

				Room curRoom = getRoomFromName(Roomlist, name);

				Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);

				curRoom.addMeeting(meeting);
			}

		return "Successfully scheduled meeting!";

	}

	protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
		return roomList.get(findRoomIndex(roomList, name));
	}

	protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
		int roomIndex = 0;
		int finalRoom=0;
		
		boolean found=false;

		for (Room room : roomList) {
			if (room.getName().compareTo(roomName) == 0) {
			found = true;

				roomIndex++;
			}

			if (!found) {
				roomIndex++;
			}
		}
		if (found) {
			finalRoom = roomIndex;
		}
		return finalRoom;
	}

	protected static String getRoomName() {
		System.out.println("Room Name?");
		return keyboard.next();
	}
} 
