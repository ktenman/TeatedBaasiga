package ee.tlu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
public class VeebiLeht {
	public VeebiLeht(int port) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			Socket socket = serverSocket.accept();
			try {
				PrintWriter kirjutaja = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader lugeja = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				kirjutaja.println("HTTP/1.1 200 OK");
				kirjutaja.println("Content-Type: text/html\n\n");
				String rida = lugeja.readLine();
				if (rida.contains(" ")) {
					String[] tykid = rida.split(" ");
					kuvaSistamine(kirjutaja);
					if (tykid[1].split("=")[0].equals("/sisesta?teade")) {
						if (!tykid[1].split("=")[1].equals("")) {
							String teade = tykid[1].split("=")[1];
							Hoidla hoidla = new Hoidla();
							if (hoidla.teatedAndmebaasist().size()>0) {
								if (!teade.equals(hoidla.teatedAndmebaasist().get(hoidla.teatedAndmebaasist().size() - 1).teade)) {
									salvestaTeadeAndmebaasi(tykid[1].split("=")[1],kirjutaja);
								} else {
									kirjutaja.println("Proovite sisestada teadet topelt.");
								};			
							} else {
								salvestaTeadeAndmebaasi(tykid[1].split("=")[1],kirjutaja);
							}
						}
					}
					jatkaSiin: if (tykid[1].split("=")[0].equals("/kustuta?id")) {
						if (tykid[1].split("=").length > 1) {
							try {
								String string = tykid[1].split("=")[1];
								string = string.replaceAll("[^0-9]+", "");
								if (string.length() == 0) {
									break jatkaSiin;
								}
								int id = Integer.parseInt(string);
								if (Hoidla.eemaldaTeade(id)) {
									kirjutaja.println("Teade kustutatud!");
								} else {
									kirjutaja.println("Teade ei kustutatud!");
								}
							} catch (NumberFormatException e) {
								kirjutaja.println("Teade ei kustutatud!");
								e.printStackTrace();
							}
						}
					}
					kuvaTeated(kirjutaja);
				} else {
					kuvaSistamine(kirjutaja);
					kuvaTeated(kirjutaja);
				}
			} catch (Exception e) {
				System.out.println("Probleem");
				e.printStackTrace();
			}
			socket.close();
		}
	}
	private void salvestaTeadeAndmebaasi(String string, PrintWriter kirjutaja) throws UnsupportedEncodingException {
		string = string.replace("%E4", "ä");
		string = java.net.URLDecoder.decode(string, "UTF-8");
		new TeateLisaja(string);
		kirjutaja.println("Teade salvestatud!");
	}
	private void kuvaSistamine(PrintWriter kirjutaja) {
		kirjutaja.println("<form action='sisesta' method='get' id='vorm'>");
		kirjutaja.println("<input type='text' name='teade' placeholder='Sisesta teade' required>");
		kirjutaja.println("<input type='submit' value='Salvesta' id='submit'>");
		kirjutaja.println("</form>");
	}
	private void kuvaTeated(PrintWriter kirjutaja) {
		Hoidla hoidla = new Hoidla();
		List < Teade > teated = hoidla.getHoida();
		if (teated.size()>0) {
			kirjutaja.println("<h2>Andmebaasis olevad teated</h2>");
			kirjutaja.println("<h3>Andmebaasis on " + teated.size() + " teadet.</h3>");
			kirjutaja.println("<table border='1'>");
			kirjutaja.println("<tr><th>id</th><th>teade</th><th>kustuta</th></tr>");
			for (Teade teade: teated) {
				String kustuta = "<a href='/kustuta?id=" + teade.id + "'><button class='kustutaTeade'>X</button></a><br />";
				kirjutaja.println("<tr><td>" + teade.id + "</td><td>" + teade.teade + "</td><td align='center'>" + kustuta + "</td></tr>");
			}
			kirjutaja.println("</table>");	
		}
	}
	public static void main(String[] args) throws IOException {
		int port = 80;
		if (args.length>0) {
			port = Integer.parseInt(args[0]);
		}
		new VeebiLeht(port);
	}
}
