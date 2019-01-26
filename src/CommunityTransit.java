/* Ngan Campbell
 * Assignment 1
 * CS320
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;
import java.util.Scanner;

public class CommunityTransit {

    public static void main(String[] args) throws Exception {

        String text = getWebpageSource("https://www.communitytransit.org/busservice/schedules/");
        String x = "Please enter a letter that your destinations start with: ";
        String input = getUserInput(x);
        Pattern p = Pattern.compile("<h3>"+input+".*");
        Matcher m = p.matcher(text);
        if(m.find() == false) {
            input = getUserInput(x);
        }
        Pattern pattern = Pattern.compile("<h3>("+input+".*)\\s*</h3>\\s*(<div\\s*(.*)>\\s*<div\\s*(.*)>\\s*<strong><a\\s*(.*)/schedules(.*)>(.*)<(.*)></strong>\\s*</div>\\s*<div\\s*(.*)</div>\\s*</div>\\s*)*");
        Pattern pattern1 = Pattern.compile("<strong><a\\s*(.*)>(.*)<(.*)></strong>");
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()) {
            Matcher matcher1 = pattern1.matcher(matcher.group(0));
            System.out.println("Destination: " + matcher.group(1));
            while(matcher1.find()) {
                System.out.println("Bus number: " + matcher1.group(2));
            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        }
        String y = "Please enter a route ID as a string: ";
        String input1 = getUserInput(y);
        input1 = input1.replace("/","-");
        String text1 = getWebpageSource("https://www.communitytransit.org/busservice/schedules/route/" + input1);
        System.out.println("\n");
        System.out.println("The link for your route is: https://www.communitytransit.org/busservice/schedules/route/" + input1);
        System.out.println("\n");
        Pattern pattern2 = Pattern.compile("<h2>Weekday<small>(.*)</small></h2>\\s*</td>\\s*</tr>\\s*<tr>\\s*<(.*)>(\\s*(<(.*)>\\s*){3}<strong(.*)>(.*)</strong>\\s*</span>\\s*<p>(.*)</p>\\s*</th>\\s*)*</tr>");
        Matcher matcher2 = pattern2.matcher(text1);
        Pattern pattern3 = Pattern.compile("<strong(.*)>(.*)</strong>\\s*</span>\\s*<p>(.*)</p>");
        while(matcher2.find()){
            Matcher matcher3 = pattern3.matcher(matcher2.group(0));
            System.out.println("Destination: " + matcher2.group(1));
            while(matcher3.find()) {
                System.out.println("Stop number: " + matcher3.group(2) + " is " + matcher3.group(3));
            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    private static String getWebpageSource(final String url) throws IOException {
        URLConnection ct = new URL(url).openConnection();
        ct.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        BufferedReader in = new BufferedReader(new InputStreamReader(ct.getInputStream()));
        String inputLine = "";
        String text = "";
        while ((inputLine = in.readLine()) != null) {
            text += inputLine + "\n";
        }
        in.close();
        return text;
    }

    private static String getUserInput(String promptUser) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(promptUser);
        String input = scanner.next();
        return input;
    }
}
