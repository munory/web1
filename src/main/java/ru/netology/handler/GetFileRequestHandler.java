package ru.netology.handler;

import ru.netology.Request;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public class GetFileRequestHandler implements RequestHandler {

    private Function<String, String> templateConverter;

    public GetFileRequestHandler() {
    }

    public GetFileRequestHandler(Function<String, String> templateConverter) {
        this.templateConverter = templateConverter;
    }

    @Override
    public void run(Request request, BufferedOutputStream out) {
        String path = request.getPath();

        if (request.getPath().startsWith("/")) {
            path = path.substring(1);
        }

        try {
            URL fileUrl = GetFileRequestHandler.class.getClassLoader().getResource("public/" + path);

            if (fileUrl != null) {
                File file = new File(fileUrl.toURI());

                try {
                    final var filePath = Path.of(file.getPath());
                    final var mimeType = Files.probeContentType(filePath);

                    if (file.getName().endsWith("html") || file.getName().endsWith("css") || file.getName().endsWith("js")) {
                        var template = Files.readString(filePath);
                        if (templateConverter != null) {
                            template = templateConverter.apply(template);
                        }
                        final var content = template.getBytes();
                        out.write((
                                "HTTP/1.1 200 OK\r\n" +
                                        "Content-Type: " + mimeType + "\r\n" +
                                        "Content-Length: " + content.length + "\r\n" +
                                        "Connection: close\r\n" +
                                        "\r\n"
                        ).getBytes());
                        out.write(content);
                        out.flush();
                    }

                    final var length = Files.size(filePath);
                    out.write((
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: " + mimeType + "\r\n" +
                                    "Content-Length: " + length + "\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n"
                    ).getBytes());
                    Files.copy(filePath, out);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    out.write((
                            "HTTP/1.1 404 Not Found\r\n" +
                                    "Content-Length: 0\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n"
                    ).getBytes());
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
