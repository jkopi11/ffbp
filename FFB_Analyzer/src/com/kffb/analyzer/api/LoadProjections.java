package com.kffb.analyzer.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class LoadProjections
 */
@WebServlet(description = "API - Load Projections", urlPatterns = "/api/loadprojections")
public class LoadProjections extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/ffbp")
	private DataSource ds;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadProjections() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String site = request.getParameter("site");
			String scoreType = request.getParameter("scoreType");
			// P_ID, PNAME, SITE_ID, SCORE_TYPE, WEEK, SCORE, 
			PreparedStatement projPS = conn.prepareStatement("CALL updatePlayers(?, ?, ?, ?, ?, ?)");
			 
			
			JSONArray projections = new JSONArray(request.getParameter("projections"));
			JSONObject proj;
			for (int i = 0; i < projections.length(); i++) {
				proj = new JSONObject(projections.getString(i));
				int pID = proj.getInt(site);
				if (pID == 0) {
					continue;
				}
				// set ID
				projPS.setInt(1, pID);

				// set Player Name
				projPS.setString(2, proj.getString("NAME"));
			
				// set SITE ID
				projPS.setString(3, site);
				
				projPS.setString(4, scoreType);

				// set Week
				projPS.setInt(5, Integer.valueOf(request.getParameter("week")));
							
				// set ESPN Proj
				projPS.setInt(6, proj.getInt(scoreType));

				projPS.addBatch();
				System.out.println("CALL updatePlayers("+pID+",'"+proj.getString("NAME")+"','"+site+"','"+scoreType+"',"+Integer.valueOf(request.getParameter("week"))+","+proj.getInt(scoreType)+");");
			}
		
			int [] projRows = projPS.executeBatch();
			
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			JSONObject jsonResponse = new JSONObject("{success : true}");
			int totalUpdates = 0;
			for (int i = 0; i < projRows.length; i++) {
				totalUpdates += projRows[i];
			}
			jsonResponse.append("projrows", totalUpdates);
			out.print(jsonResponse);
			out.flush();
			//use conn
			conn.commit();
			conn.close();
		} catch (JSONException je) {
			je.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

}
