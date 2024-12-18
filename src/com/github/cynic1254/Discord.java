package com.github.cynic1254;

import groovy.json.JsonOutput;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Discord {
    public static class WebhookMessage {
        public String username = null;
        public String avatar_url = null;
        public String content = null;
        public List<Embed> embeds = new ArrayList<>();
        public Boolean tts = null;
        public AllowedMentions allowed_mentions = null;

        public static class Embed {
            public Integer color = 0;
            public Author author = null;
            public String title = null;
            public String url = null;
            public String description = null;
            public List<Field> fields = new ArrayList<>();
            public Image thumbnail = null;
            public Image image = null;
            public Footer footer = null;
            public String timestamp = null;

            public static class Author {
                public String name = null;
                public String url = null;
                public String icon_url = null;
            }

            public static class Field {
                public String name = null;
                public String value = null;
                public Boolean inline = null;
            }

            public static class Image {
                public String url = null;
            }

            public static class Footer {
                public String text = null;
                public String icon_url = null;
            }
        }

        public static class AllowedMentions {
            public List<String> parse = new ArrayList<>();
            public List<String> roles = new ArrayList<>();
            public List<String> users = new ArrayList<>();
        }

        public String ToJson() {
            return JsonOutput.toJson(this).replace("\"", "\"\"");
        }

        public void Send(String URL) {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(ToJson())).build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("Response status code: " + response.statusCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static WebhookMessage BasicStatusTemplate(String title, int color, String fieldName, int changelist, String jobUrl, String jobName, int jobNumber) {
            WebhookMessage message = new WebhookMessage();
            Embed embed = new Embed();

            embed.title = title;
            embed.color = color;

            Embed.Field field = new Embed.Field();
            field.name = fieldName;
            field.value = "Last Changelist: " + changelist;

            embed.fields.add(field);

            Embed.Field field2 = new Embed.Field();
            field2.name = "Job url";
            field2.value = jobUrl;

            embed.fields.add(field2);

            embed.footer = new Embed.Footer();
            embed.footer.text = jobName + '(' + jobNumber + ')';

            message.embeds.add(embed);

            return message;
        }
    }
}