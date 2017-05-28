package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EntrepriseDAO {
	
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String LOGIN = "bdd1"; //exemple BDD1
	final static String PASS = "bdd1"; //exemple BDD1

	public EntrepriseDAO() {
	try {
	Class.forName("oracle.jdbc.OracleDriver");
	} catch (ClassNotFoundException e) {
	System.err.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
	}
	}
	
	public int ajouter(Entreprise entreprise) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("INSERT INTO entreprise (ent_nom, ent_ape, ent_adresse, ent_nSiret) VALUES (?, ?, ?, ?)");
			ps.setString(1, entreprise.getNom());
			ps.setString(2, entreprise.getApe());
			ps.setString(3, entreprise.getAdresse());
			ps.setInt(4, entreprise.getnSiret());
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ignore) {}
			try {if (con != null) con.close();} catch (Exception ignore) {}
		}
		return retour;
	}

	public Entreprise getEntreprise(int idEnt) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Entreprise retour = null;
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM entreprise WHERE ent_id = ?");
			ps.setInt(1, idEnt);
			rs = ps.executeQuery();
			if (rs.next())
				retour = new Entreprise(rs.getString("ent_nom"), rs.getInt("ent_nSiret"), rs.getString("ent_adresse"),rs.getString("ent_ape"),rs.getString("ent_mtp"));
			} 
		catch (Exception ee) {
			ee.printStackTrace();
			} 
		finally {
			try { if (rs != null) rs.close();} catch (Exception ignore) {}
			try { if (ps != null) ps.close();} catch (Exception ignore) {}
			try { if (con != null) con.close();} catch (Exception ignore) {}
			}
			return retour;
			}
	
		public int getIdEnt(String nom) {
		int retour = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM entreprise WHERE ent_nom = ?");
			ps.setString(1, nom);
			rs = ps.executeQuery();
			retour = (rs.getInt("ent_id"));
		}
		catch (Exception ee) {
			ee.printStackTrace();
			} 
		finally {
			try { if (rs != null) rs.close();} catch (Exception ignore) {}
			try { if (ps != null) ps.close();} catch (Exception ignore) {}
			try { if (con != null) con.close();} catch (Exception ignore) {}
			}
		return retour;
	}
	
	public int updateEntreprise(int ident, String nom, int nSiret, String adresse, String ape) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("UPDATE entreprise SET ent_nom = ?, ent_nSiret = ?, ent_adresse = ?, ent_ape = ?  WHERE idEnt = ?");
			ps.setString(1, nom);
			ps.setInt(2, nSiret);
			ps.setString(3, adresse);
			ps.setString(4, ape);
			ps.setInt(5, ident);
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ignore) {}
			try {if (con != null) con.close();} catch (Exception ignore) {}
		}
		return retour;
	}
}
