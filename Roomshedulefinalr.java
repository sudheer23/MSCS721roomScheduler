package sudheer;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.sql.Timestamp;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RoomScheduler {
	private static final String JSONObject = null;
	protected static Scanner keyboard = new Scanner(System.in);

	@SuppressWarnings({ "static-access", "static-access" })
	public static void main(String[] args) {
		Boolean end = false;
		ArrayList<Room> rooms = new ArrayList<Room>();
		try {
			while (!end) {
				switch (mainMenu()) {

				case 1:
					System.out.println(addRoom(rooms));
					break;
				case 2:
					System.out.println(removeRoom(rooms));
					break;
				case 3:
					System.out.print(scheduleRoom(rooms));
					break;
				case 4:
					System.out.println(listSchedule(rooms));
					break;
				case 5:
					System.out.println(listRooms(rooms));
				case 6 :
					exportRooms(rooms);
				case 7 :
					importRooms (rooms);
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Print Number Only");
			System.exit(0);
		}
	}

	
	
	private static void exportRooms(ArrayList<Room> roomList) throws JSONException 
	{
		
		
		JSONArray roomObj = new JSONArray();
		JSONObject singleObj = new JSONObject();
			for (Room room : roomList) 
				{
			//		JSONObject singleObj = new JSONObject();
		
					singleObj.put("roomName", room.getName());
					singleObj.put("capacity", room.getCapacity());
					//((List<Room>) roomObj).addAll((Collection<? extends Room>) singleObj);
				//	roomObj.put(singleObj); 
					 for (Meeting m : room.getMeetings()) 
					 {
						 //	JSONObject singleObj = new JSONObject();
						 	
							singleObj.put("start", m.getStartTime().toString());
							singleObj.put("stop", m.getStopTime().toString());
							//System.out.println(singleObj);
					
							//((List<Room>) meetingArrayObj).addAll((Collection<? extends Room>) singleObj);
					 
					 }	

						roomObj.put(singleObj);	
				}
			
			
			System.out.println("Successfully Copied JSON Object to File...");
		
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
				
		
	}

	@SuppressWarnings("unchecked")
	private static void exportSchedule(ArrayList<Room> roomList) throws JSONException {
		
		
		
		
		JSONArray scheduleObj = new JSONArray();
		JSONObject singleRoomObj = new JSONObject();
		for (Room room : roomList) 
		{
			//System.out.println(room.getName());
			
			JSONArray meetingArrayObj = new JSONArray();
		 for (Meeting m : room.getMeetings()) 
		 {
			 	JSONObject singleObj = new JSONObject();
			 	
				singleObj.put("start", m.getStartTime().toString());
				singleObj.put("stop", m.getStopTime().toString());
				//System.out.println(singleObj);
				((List<Room>) meetingArrayObj).addAll((Collection<? extends Room>) singleObj);
		 }
		 
		 singleRoomObj.put(room.getName(), meetingArrayObj);
		 //System.out.println(singleRoomObj);
		 
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

	private static void importRooms(ArrayList<Room> roomList) throws JSONException 
	{

		JSONArray scheduleObj = new JSONArray();
		JSONObject singleRoomObj = new JSONObject();
		 
		 	try {
				int currLine;
		 			// read data from the file

				singleRoomObj = new JSONObject(new Room(
		 	//				"D:\\JavaWorkSpace\\result.json"));
		 
		 			while ((currLine = JSONObject.Room()) != null) {
		 				Object obj;
		 				try {
		 				obj = JSONObject.substring(currLine);
		 					JSONObject jsonObject = (JSONObject) obj;
		 					String name = (String) jsonObject.get("RoomName");
		 				long capacity = (long) jsonObject.get("Capacity");
		 					// add the imported data to the roomList
		 					Room room = new Room(name, (int) capacity);
		 					roomList.add(room);
		 
		 				} catch (IOException e1) {
		 					// TODO Auto-generated catch block
		 					e1.printStackTrace();
		 				}
		 			}
		 			System.out.println("Data imported successfully");
		 	//	} catch (IOException e) {
		 		//	e.printStackTrace();
		 		} finally {
		 			try {
		 		//		if (JSONObject != null)
		 				JSONObject.Room(); // close the buffered reader
		 			} catch (IOException ex) {
		 				ex.printStackTrace();
		 			}
		 		}
		 	
	}
	
	
	protected static int mainMenu() {
		System.out.println("Main Menu:");
		System.out.println("  1 - Add a room");
		System.out.println("  2 - Remove a room");
		System.out.println("  3 - Schedule a room");
		System.out.println("  4 - List Schedule");
		System.out.println("  5 - List Rooms");
		System.out.println("6 - export rooms");
		System.out.println("7 - importrooms ");

		return keyboard.nextInt();
	}

	protected static String addRoom(ArrayList<Room> roomList) {
		System.out.println("Add a room:");
		String name = getRoomName();
		System.out.println("Room capacity?");
		try {
			int capacity = keyboard.nextInt();

			Room newRoom = new Room(name, capacity);
			roomList.add(newRoom);

			return "Room '" + newRoom.getName() + "' added successfully!";
		} catch (Exception e) {
			return "Room " + "not" + "added";
		}

	}

	protected static String removeRoom(ArrayList<Room> roomList) {
		System.out.println("Remove a room:");
		roomList.remove(findRoomIndex(roomList, getRoomName()));

		return "Room removed successfully!";
	}

	protected static String listRooms(ArrayList<Room> roomList) {
		System.out.println("Room Name - Capacity");
		System.out.println("---------------------");

		for (Room room : roomList) {
			System.out.println(room.getName() + " - " + room.getCapacity());
		}

		System.out.println("---------------------");

		return roomList.size() + " Room(s)";
	}

	protected static String scheduleRoom(ArrayList<Room> roomList) {
		System.out.println("Schedule a room:");
		String name = getRoomName();
		for (int index = 0; index < roomList.size(); index++)

			if (roomList.get(index).toString().compareTo(name) == 1) {
				roomList.get(index);
				System.out.println("Already Exists");

			} else {
				System.out.println("Start Date? (yyyy-mm-dd hh:mm:ss.fffffffff):");
				String startDate = keyboard.next();
				System.out.println("Start Time?");
				String startTime = keyboard.next();
				startTime = startTime + ":00.0";

				System.out.println("End Date? (yyyy-mm-dd hh:mm:ss.fffffffff):");
				String endDate = keyboard.next();
				System.out.println("End Time?");
				String endTime = keyboard.next();
				endTime = endTime + ":00.0";

				//Timestamp startTimestamp = Timestamp.valueOf(startDate + " " + startTime);
			//	Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);

				Timestamp startTimestamp = Timestamp.valueOf("2016-02-10 10:00:00");
				Timestamp endTimestamp = Timestamp.valueOf("2016-02-11 10:00:00");
				System.out.println("Subject?");
				String subject = keyboard.nextLine();

				Room curRoom = getRoomFromName(roomList, name);

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

		for (Room room : roomList) {
			if (room.getName().compareTo(roomName) == 0) {
				break;
			}
			roomIndex++;
		}

		return roomIndex;
	}

	protected static String getRoomName() {
		System.out.println("Room Name?");
		return keyboard.next();
	}
}
