package ru.yandex.practicum.Server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.manager.Managers;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.io.*;
import java.net.InetSocketAddress;

import java.util.HashMap;
import java.util.Map;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HttpTaskServer {
    private static File file = new File("./Files" , "FileBackedTasks.csv");
    private static TaskManager fileBackedTasksManager = Managers.getDefaultFileBacked(file);

    public static void main(String[] args) throws Exception {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);

        httpServer.createContext("/tasks/task", new TasksHandler());
        httpServer.createContext("/tasks/epic", new EpicsHandler());
        httpServer.createContext("/tasks/subtask", new SubtasksHandler());
        httpServer.createContext("/tasks/subtasks/epic", new GetEpicSubtasks());
        httpServer.createContext("/tasks/history", new GetHistory());
        httpServer.createContext("/tasks", new GetPrioritizedTasks());

        httpServer.start();
    }


    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new Gson();
            String postSerialized = "";
            String method = httpExchange.getRequestMethod();
            switch (method){
                case "GET":
                    Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
                    if(params.containsKey("id") && !params.get("id").isEmpty()) {
                        try {
                            postSerialized = gson.toJson(fileBackedTasksManager.getTask(Long.parseLong(params.get("id"))));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        postSerialized = gson.toJson(fileBackedTasksManager.getTaskList());
                    }

                    httpExchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(postSerialized.getBytes());
                    }
                    httpExchange.close();
                    break;
                case "POST":
                    InputStreamReader streamReader = new InputStreamReader(httpExchange.getRequestBody(), DEFAULT_CHARSET);
                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    String body = bufferedReader.readLine();
                    Task taskFromGson = gson.fromJson(body, Task.class);
                    try {
                        if (fileBackedTasksManager.getTask(taskFromGson.getId()) != null) {
                            httpExchange.sendResponseHeaders(404, 0);
                            httpExchange.close();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Task task = new Task(taskFromGson.name, taskFromGson.description);
                    try {
                        fileBackedTasksManager.addTask(task);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    httpExchange.sendResponseHeaders(200, 0);
                    httpExchange.close();
                    break;
                case "DELETE":
                    Map<String, String> paramsDELETE = queryToMap(httpExchange.getRequestURI().getQuery());
                    if(paramsDELETE.containsKey("id") && !paramsDELETE.get("id").isEmpty()) {
                        fileBackedTasksManager.removeTask(Long.parseLong(paramsDELETE.get("id")));
                    } else {
                        fileBackedTasksManager.clearTask();
                    }

                    httpExchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(postSerialized.getBytes());
                    }
                    httpExchange.close();
                    break;
            }


        }
    }

    static class EpicsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new Gson();
            String postSerialized = "";
            String method = httpExchange.getRequestMethod();

            switch (method) {
                case "GET":
                    Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
                    if(params.containsKey("id") && !params.get("id").isEmpty()) {
                        try {
                            postSerialized = gson.toJson(fileBackedTasksManager.getEpic(Long.parseLong(params.get("id"))));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        postSerialized = gson.toJson(fileBackedTasksManager.getEpicList());
                    }

                    httpExchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(postSerialized.getBytes());
                    }
                    break;
                case "POST":
                    InputStreamReader streamReader = new InputStreamReader(httpExchange.getRequestBody(), DEFAULT_CHARSET);
                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    String body = bufferedReader.readLine();
                    Task taskFromGson = gson.fromJson(body, Epic.class);
                    try {
                        if (fileBackedTasksManager.getEpic(taskFromGson.getId()) != null) {
                            httpExchange.sendResponseHeaders(404, 0);
                            httpExchange.close();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Epic epic = new Epic(taskFromGson.name, taskFromGson.description);
                    try {
                        fileBackedTasksManager.addEpic(epic);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    httpExchange.sendResponseHeaders(200, 0);
                    httpExchange.close();
                    break;
                case "DELETE":
                    Map<String, String> paramsDELETE = queryToMap(httpExchange.getRequestURI().getQuery());
                    if(paramsDELETE.containsKey("id") && !paramsDELETE.get("id").isEmpty()) {
                        fileBackedTasksManager.removeEpic(Long.parseLong(paramsDELETE.get("id")));
                    } else {
                        fileBackedTasksManager.clearEpic();
                    }

                    httpExchange.sendResponseHeaders(200, 0);
                    httpExchange.close();
                    break;
            }

        }
    }

    static class SubtasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new Gson();
            String postSerialized = "";
            String method = httpExchange.getRequestMethod();

            switch (method) {
                case "GET":
                    Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
                    if(params.containsKey("id") && !params.get("id").isEmpty()) {
                        try {
                            postSerialized = gson.toJson(fileBackedTasksManager.getSubtask(Long.parseLong(params.get("id"))));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        postSerialized = gson.toJson(fileBackedTasksManager.getSubtaskList());
                    }

                    httpExchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(postSerialized.getBytes());
                    }
                    break;
                case "POST":
                    InputStreamReader streamReader = new InputStreamReader(httpExchange.getRequestBody(), DEFAULT_CHARSET);
                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    String body = bufferedReader.readLine();
                    Subtask taskFromGson = gson.fromJson(body, Subtask.class);
                    try {
                        if (fileBackedTasksManager.getSubtask(taskFromGson.getId()) != null) {
                            httpExchange.sendResponseHeaders(404, 0);
                            httpExchange.close();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Subtask subtask = new Subtask(taskFromGson.name, taskFromGson.description);
                    try {
                        fileBackedTasksManager.addSubtask(subtask, taskFromGson.getParentId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    httpExchange.sendResponseHeaders(200, 0);
                    httpExchange.close();
                    break;
                case "DELETE":
                    Map<String, String> paramsDELETE = queryToMap(httpExchange.getRequestURI().getQuery());
                    if(paramsDELETE.containsKey("id") && !paramsDELETE.get("id").isEmpty()) {
                        fileBackedTasksManager.removeSubtask(Long.parseLong(paramsDELETE.get("id")));
                    } else {
                        fileBackedTasksManager.clearSubtask();
                    }

                    httpExchange.sendResponseHeaders(200, 0);
                    httpExchange.close();
                    break;
            }
        }
    }

    static class GetEpicSubtasks implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new Gson();
            String postSerialized = "";

            Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
            if(params.containsKey("id") && !params.get("id").isEmpty()) {
                postSerialized = gson.toJson(fileBackedTasksManager.getEpicSubtasks(Long.parseLong(params.get("id"))));
            }

            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(postSerialized.getBytes());
            }
        }
    }

    static class GetHistory implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new Gson();
            String postSerialized = "";

                postSerialized = gson.toJson(fileBackedTasksManager.history());

            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(postSerialized.getBytes());
            }
        }
    }

    static class GetPrioritizedTasks implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new Gson();
            String postSerialized = "";

            postSerialized = gson.toJson(fileBackedTasksManager.getPrioritizedTasks());

            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(postSerialized.getBytes());
            }
        }
    }

    private static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if(query == null) {
            return result;
        }
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

}
