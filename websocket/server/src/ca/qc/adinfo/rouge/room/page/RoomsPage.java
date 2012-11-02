package ca.qc.adinfo.rouge.room.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.room.Room;
import ca.qc.adinfo.rouge.room.RoomManager;
import ca.qc.adinfo.rouge.server.servlet.RougeServerPage;
import ca.qc.adinfo.rouge.user.User;

public class RoomsPage extends RougeServerPage {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		RoomManager roomManager = (RoomManager)RougeServer.getInstance().getModule("room.manager");
		Collection<Room> rooms = roomManager.getRooms();

		PrintWriter out = response.getWriter();

		this.drawHeader(out);
		this.startBox("Rooms", out);
		this.startList(out);

		for(Room room: rooms) {
			this.listItem(room.getName() , out);
			
			this.startList(out);
			
			for(User user: room.getPeopleInRoom()) {
				this.listItem(user.getUsername(), out);
			}

			this.endList(out);
		}

		this.endList(out);
		this.endBox(out);
		this.drawFooter(out);
	}

}
