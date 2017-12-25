package io.github.ilyabystrov.urlshortener.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/help")
public class HelpController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String printHelp() {

    // hardcoded for simplicity
    return "#############\n" +
        "Url Shortener\n" +
        "#############\n" +
        "\n" +
        "Building\n" +
        "========\n" +
        "\n" +
        "Checkout source code and run \n" +
        "\n" +
        "::\n" +
        "  \n" +
        "  $ mvn clean package -DskipTests\n" +
        "\n" +
        "Running\n" +
        "=======\n" +
        "\n" +
        "::\n" +
        "\n" +
        "  $ java -jar target/urlshortener-0.0.1-SNAPSHOT.jar\n" +
        "\n" +
        "Typical Scenario\n" +
        "================\n" +
        "\n" +
        "This doc is also avaialable at '/help/':\n" +
        "\n" +
        "::\n" +
        "\n" +
        "  $ curl  localhost:8080/help/ | less\n" +
        "\n" +
        "Create testAccountX\n" +
        "-------------------\n" +
        "\n" +
        "::\n" +
        "\n" +
        "  $ curl -H 'Content-Type: application/json' -X POST localhost:8080/config/account -d '{ \"accountId\": \"testAccountX\"}'; echo\n" +
        "  {\"success\":true,\"description\":\"Account was created\",\"password\":\"YVlBF3bE\"}\n" +
        "\n" +
        "Register url with default (302) redirect \n" +
        "-----------------------------------------\n" +
        "\n" +
        "::\n" +
        "\n" +
        "  $ curl -u testAccountX:YVlBF3bE -H 'Content-Type: application/json' -X POST localhost:8080/config/register -d '{ \"url\": \"https://google.com/search?q=test1\"}'; echo\n" +
        "  {\"shortUrl\":\"http://localhost:8080/aA4\",\"redirectType\":302}\n" +
        "\n" +
        "Register url with 301 redirect \n" +
        "-------------------------------\n" +
        "\n" +
        "::\n" +
        "\n" +
        "  $ curl -u testAccountX:YVlBF3bE -H 'Content-Type: application/json' -X POST localhost:8080/config/register -d '{ \"url\": \"https://google.com/search?q=test2\", \"redirectType\": 301}'; echo\n" +
        "  {\"shortUrl\":\"http://localhost:8080/aA5\",\"redirectType\":301}\n" +
        "\n" +
        "Check redirecting for short urls\n" +
        "--------------------------------\n" +
        "\n" +
        "::\n" +
        "\n" +
        "  $ curl -v http://localhost:8080/aA4\n" +
        "  + Hostname was NOT found in DNS cache\n" +
        "  + Trying 127.0.0.1...\n" +
        "  + Connected to localhost (127.0.0.1) port 8080 (#0)\n" +
        "  > GET /aA4 HTTP/1.1\n" +
        "  > User-Agent: curl/7.35.0\n" +
        "  > Host: localhost:8080\n" +
        "  > Accept: */*\n" +
        "  >\n" +
        "  < HTTP/1.1 302\n" +
        "  < X-Application-Context: application\n" +
        "  < Location: https://google.com/search?q=test1\n" +
        "  < Content-Length: 0\n" +
        "  < Date: Mon, 25 Dec 2017 11:36:17 GMT\n" +
        "  <\n" +
        "  + Connection #0 to host localhost left intact\n" +
        "\n" +
        "\n" +
        "::\n" +
        "\n" +
        "  $ curl -v http://localhost:8080/aA4\n" +
        "  + Hostname was NOT found in DNS cache\n" +
        "  + Trying 127.0.0.1...\n" +
        "  + Connected to localhost (127.0.0.1) port 8080 (#0)\n" +
        "  > GET /aA4 HTTP/1.1\n" +
        "  > User-Agent: curl/7.35.0\n" +
        "  > Host: localhost:8080\n" +
        "  > Accept: */*\n" +
        "  >\n" +
        "  < HTTP/1.1 302\n" +
        "  < X-Application-Context: application\n" +
        "  < Location: https://google.com/search?q=test1\n" +
        "  < Content-Length: 0\n" +
        "  < Date: Mon, 25 Dec 2017 11:36:17 GMT\n" +
        "  <\n" +
        "  + Connection #0 to host localhost left intact\n" +
        "\n" +
        "Use redirect multiple times for statistic\n" +
        "-----------------------------------------\n" +
        "  \n" +
        "  Note that browsers can caches '301 direct' and don't request original location in future.\n" +
        "\n" +
        "Check statistic\n" +
        "---------------\n" +
        "\n" +
        "::\n" +
        "\n" +
        "  $ curl -u testAccountX:YVlBF3bE -H 'Content-Type: application/json' localhost:8080/statistic/testAccountX ; echo\n" +
        "  {\"https://google.com/search?q=test1\":5,\"https://google.com/search?q=test2\":8}\n";
  }
}
