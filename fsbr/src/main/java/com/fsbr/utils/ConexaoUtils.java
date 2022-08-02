package com.fsbr.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexaoUtils {

	public static String getRequest(String endpoint) {
		StringBuffer response = new StringBuffer();
		URL url = null;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("Accept", "application/json");
			httpURLConnection.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(response.toString());
				response.append(inputLine);
			}

			if (httpURLConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + httpURLConnection.getResponseCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpURLConnection.disconnect();
		}
		return response.toString();

	}
	
	public static void putRequest(String endpoint, String body) throws Exception {
		URL url = null;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException exception) {
			exception.printStackTrace();
		}
		HttpURLConnection httpURLConnection = null;
		OutputStreamWriter wout = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			httpURLConnection.setRequestMethod("PUT");
			httpURLConnection.setDoOutput(true);

	        wout = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
	        wout.write(body);
	        wout.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
		} catch (IOException exception) {
			exception.printStackTrace();
			String mensagem = "Houve algum problema na alteração, tentar novamente.";
			throw new Exception(mensagem);
		} finally {
			if (wout != null) {
				try {
					wout.flush();
					wout.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}

	public static void deleteRequest(String endpoint) throws Exception {
		URL url = null;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException exception) {
			exception.printStackTrace();
		}
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("Accept", "application/json");
			httpURLConnection.setRequestMethod("DELETE");
			httpURLConnection.connect();
			System.out.println(httpURLConnection.getResponseCode());
		} catch (IOException e) {
			e.printStackTrace();
			String mensagem = "“Houve algum problema na exclusão, tentar novamente.";
			throw new Exception(mensagem);
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}

	public static void postRequest(String endpoint, String body) throws Exception {
		URL url = null;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection httpURLConnection = null;

		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
			String mensagem = "Houve algum problema na inclusão, tentar novamente.";
			throw new Exception(mensagem);
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}

	}
}
