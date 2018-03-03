package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.*;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    // Паттерн для поиска данных в логе
    private final static String GROUP_PATTERN = "(?<ip>[\\d]+.[\\d]+.[\\d]+.[\\d]+)\\s(?<user>[a-zA-Z ]+)\\s(?<date>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\\s(?<event>[\\w]+)\\s?((?<taskNumber>[\\d]+)|)\\s(?<status>[\\w]+)";
    // Паттерн для поиска данных в запросе
    private final static String QUERY_PATTERN = "get\\s(?<field1>\\w+)(\\sfor\\s(?<field2>\\w+)\\s=\\s\"(?<value>.{1,40})\")?(\\sand date between\\s\"(?<after>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\"\\sand\\s\"(?<before>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\")?";

    // Константи в строке с логом
    private final static String IP = "ip";
    private final static String USER = "user";
    private final static String DATE = "date";
    private final static String EVENT = "event";
    private final static String TASK_NUMBER = "taskNumber";
    private final static String STATUS = "status";

    // Константи в строке с запросом
    private final static String FIELD1 = "field1";
    private final static String FIELD2 = "field2";
    private final static String VALUE = "value";
    private final static String AFTER = "after";
    private final static String BEFORE = "before";

    // Формат времени в логе и запросе
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    // Путь к директории с логами
    private Path logDir;

    // Логи
    private List<LogObject> logs = new ArrayList<>();

    /**
     * Конструктор
     */
    public LogParser(Path logDir) {
        this.logDir = logDir;
        updateLogs();
    }

    /**
     * Обновляет логи
     */
    private void updateLogs() {
        logs = getLogs();
    }

    /**
     * Возвращае список логов считаных в файлах заданой директории
     *
     * @return List<LogObject>
     */
    private List<LogObject> getLogs() {
        List<LogObject> logs = new ArrayList<>();
        List<String> strings = readLogs();

        // Создаем обект для каждого лога
        for (String string : strings) {
            String ip = findIP(string);
            String user = findUser(string);
            Date date = findDate(string);
            Event event = findEvent(string);
            Status status = findStatus(string);

            // Проверка на количество номер сделаной задачи
            if (isValidLog(ip, user, date, event, status)) {
                int number = -1;
                if (event == Event.DONE_TASK || event == Event.SOLVE_TASK)
                    number = findTaskNumber(string);

                logs.add(new LogObject(ip, user, date, event, number, status));
            }
        }
        return logs;
    }

    /**
     * Проверяет валидний ли лог
     */
    private boolean isValidLog(String ip, String user, Date date, Event event, Status status) {
        return ip != null
                && user != null
                && date != null
                && event != null
                && status != null;
    }

    /**
     * Ищет и читае все файли или же файл в директории по строкам
     */
    private List<String> readLogs() {
        List<String> logs = new ArrayList<>();
        for (File file : new File(logDir.toString()).listFiles()) {
            if (file.isFile() && file.getName().endsWith(".log")) {
                List<String> fileLogs = readFileByLine(file);
                if (fileLogs != null) logs.addAll(fileLogs);
            }
        }
        return logs;
    }

    /**
     * Читает файл по строкам
     */
    private List<String> readFileByLine(File file) {
        try (FileInputStream fis = new FileInputStream(file.toString());
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            List<String> logs = new ArrayList<>();

            // Читаем строку
            while (reader.ready()) logs.add(reader.readLine());

            return logs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Проверяет входит ли date в заданое время
     */
    private boolean inTime(Date date, Date after, Date before) {
        if (after == null && before == null) return true;
        if (after == null) return date.getTime() < before.getTime();
        if (before == null) return date.getTime() > after.getTime();

        return date.getTime() > after.getTime() && date.getTime() < before.getTime();
    }

    /**
     * Конвертирует дату котороя записа в String -> Date
     */
    private Date convertToDate(String stringDate) {
        try {
            if (stringDate != null)
                return formatter.parse(stringDate);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return null;
    }

    /*
    * Find in log methods
    *
    *
    *
    *
    */

    /**
     * Ищет IP в строке по заданому паттерну
     */
    private String findIP(String log) {
        return findRegLog(log, IP);
    }

    /**
     * Ищет User в строке по заданому паттерну
     */
    private String findUser(String log) {
        return findRegLog(log, USER);
    }

    /**
     * Ищет Date в строке по заданому паттерну
     */
    private Date findDate(String log) {
        return convertToDate(findRegLog(log, DATE));
    }

    /**
     * Ищет Event в строке по заданому паттерну
     */
    private Event findEvent(String log) {
        String eventString = findRegLog(log, EVENT);
        return eventString != null ?
                Arrays.stream(Event.values())
                        .filter(event -> event.toString().equals(eventString))
                        .findFirst().orElse(null)
                : null;
    }

    /**
     * Ищет номер задачи в строке по заданому паттерну
     */
    private int findTaskNumber(String log) {
        String number = findRegLog(log, TASK_NUMBER);
        return number != null ? Integer.parseInt(number) : -1;
    }

    /**
     * Ищет Status в строке по заданому паттерну
     */
    private Status findStatus(String log) {
        String statusString = findRegLog(log, STATUS);
        return statusString != null ?
                Arrays.stream(Status.values()).
                        filter(status -> status.toString().equals(statusString))
                        .findFirst().orElse(null)
                : null;
    }

    private String findRegLog(String string, String group) {
        return findReg(string, group, GROUP_PATTERN);
    }

    /**
     * Ищет данние в строке по заданому паттерну
     */
    private String findReg(String string, String group, String currentPattern) {
        Pattern pattern = Pattern.compile(currentPattern);
        Matcher matcher = pattern.matcher(string);

        return matcher.find() ? matcher.group(group) : null;
    }

    private boolean isLogOfUser(LogObject log, String user) {
        return log.getUser().equals(user);
    }

    /*
    * Event methods
    *
    *
    *
    *
    *
    */

    private boolean isEvent(Event event, LogObject log) {
        return log.getEvent() == event;
    }

    private boolean isLoginEvent(Event event) {
        return event == Event.LOGIN;
    }

    private boolean isDownloadPluginEvent(Event event) {
        return event == Event.DOWNLOAD_PLUGIN;
    }

    private boolean isWriteMessageEvent(Event event) {
        return event == Event.WRITE_MESSAGE;
    }

    private boolean isSolveEvent(Event event) {
        return event == Event.SOLVE_TASK;
    }

    private boolean isSolveTaskEvent(LogObject log, int task) {
        return log.getEvent() == Event.SOLVE_TASK && isEqualsTask(log, task);
    }

    private boolean isDoneEvent(Event event) {
        return event == Event.DONE_TASK;
    }

    private boolean isDoneTaskEvent(LogObject log, int task) {
        return log.getEvent() == Event.DONE_TASK && isEqualsTask(log, task);
    }

    private boolean isEqualsTask(LogObject log, int task) {
        return log.getNumberTask() == task;
    }

    /*
    * Status methods
    *
    *
    *
    *
    *
    */

    private boolean isFailedStatus(Status status) {
        return status == Status.FAILED;
    }

    private boolean isErrorStatus(Status status) {
        return status == Status.ERROR;
    }

     /*
     *  Stream methods
     *
     *
     *
     *
     *
     */

    private Stream<LogObject> getLogsStream() {
        return logs.stream();
    }

    private Stream<LogObject> filterByTime(Date after, Date before) {
        return getLogsStream().filter(log -> inTime(log.getDate(), after, before));
    }

    /*********************************************************************************************
     *
     * IPQuery methods
     *
     * *******************************************************************************************/

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) throws IOException {
        return filterByTime(after, before)
                .map(LogObject::getIp)
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return filterByTime(after, before)
                .map(LogObject::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return getLogsStream()
                .filter(log -> isLogOfUser(log, user))
                .filter(log -> inTime(log.getDate(), after, before))
                .map(LogObject::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return getLogsStream()
                .filter(log -> isEvent(event, log))
                .filter(log -> inTime(log.getDate(), after, before))
                .map(LogObject::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return getLogsStream()
                .filter(log -> log.getStatus() == status)
                .filter(log -> inTime(log.getDate(), after, before))
                .map(LogObject::getIp)
                .collect(Collectors.toSet());
    }

    /*********************************************************************************************
     *
     * UserQuery methods
     *
     * *******************************************************************************************/

    @Override
    public Set<String> getAllUsers() {
        return getLogsStream()
                .map(LogObject::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return (int) filterByTime(after, before)
                .map(LogObject::getUser)
                .count();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return (int) getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && isLogOfUser(log, user))
                .count();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && log.getIp().equals(ip))
                .map(LogObject::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && isLoginEvent(log.getEvent()))
                .map(LogObject::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && isDownloadPluginEvent(log.getEvent()))
                .map(LogObject::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && isWriteMessageEvent(log.getEvent()))
                .map(LogObject::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && log.getEvent().equals(Event.SOLVE_TASK))
                .map(LogObject::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && isSolveTaskEvent(log, task))
                .map(LogObject::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && log.getEvent().equals(Event.DONE_TASK))
                .map(LogObject::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && isDoneTaskEvent(log, task))
                .map(LogObject::getUser)
                .collect(Collectors.toSet());
    }

    /*********************************************************************************************
     *
     * DateQuery methods
     *
     * *******************************************************************************************/

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return filterByTime(after, before)
                .filter(log -> isLogOfUser(log, user) && log.getEvent().equals(event))
                .map(LogObject::getDate)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return getLogsStream()
                .filter(log -> inTime(log.getDate(), after, before) && isFailedStatus(log.getStatus()))
                .map(LogObject::getDate)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return filterByTime(after, before)
                .filter(log -> isErrorStatus(log.getStatus()))
                .map(LogObject::getDate)
                .collect(Collectors.toSet());
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Set<Date> set = filterByTime(after, before)
                .filter(log -> isLogOfUser(log, user))
                .filter(log -> isLoginEvent(log.getEvent()))
                .map(LogObject::getDate)
                .collect(Collectors.toSet());

        return set.isEmpty() ? null : Collections.min(set, Comparator.comparingLong(Date::getTime));
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        Set<Date> set = filterByTime(after, before)
                .filter(log -> isLogOfUser(log, user))
                .filter(log -> isSolveTaskEvent(log, task))
                .map(LogObject::getDate)
                .collect(Collectors.toSet());

        return set.isEmpty() ? null : Collections.min(set, Comparator.comparingLong(Date::getTime));
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Set<Date> set = filterByTime(after, before)
                .filter(log -> isLogOfUser(log, user))
                .filter(log -> isDoneTaskEvent(log, task))
                .map(LogObject::getDate)
                .collect(Collectors.toSet());

        return set.isEmpty() ? null : Collections.min(set, Comparator.comparingLong(Date::getTime));
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return filterByTime(after, before)
                .filter(log -> isLogOfUser(log, user))
                .filter(log -> isWriteMessageEvent(log.getEvent()))
                .map(LogObject::getDate)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return filterByTime(after, before)
                .filter(log -> isLogOfUser(log, user))
                .filter(log -> isDownloadPluginEvent(log.getEvent()))
                .map(LogObject::getDate)
                .collect(Collectors.toSet());
    }

    /*********************************************************************************************
     *
     * EventQuery methods
     *
     * *******************************************************************************************/

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return filterByTime(after, before)
                .map(LogObject::getEvent)
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return filterByTime(after, before)
                .map(LogObject::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return filterByTime(after, before)
                .filter(log -> log.getIp().equals(ip))
                .map(LogObject::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return filterByTime(after, before)
                .filter(log -> isLogOfUser(log, user))
                .map(LogObject::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return filterByTime(after, before)
                .filter(log -> isFailedStatus(log.getStatus()))
                .map(LogObject::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return filterByTime(after, before)
                .filter(log -> isErrorStatus(log.getStatus()))
                .map(LogObject::getEvent)
                .collect(Collectors.toSet());
    }


    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return (int) filterByTime(after, before)
                .filter(log -> isSolveTaskEvent(log, task))
                .map(LogObject::getEvent)
                .count();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return (int) filterByTime(after, before)
                .filter(log -> isDoneTaskEvent(log, task))
                .map(LogObject::getEvent)
                .count();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        filterByTime(after, before)
                .filter(log -> isSolveEvent(log.getEvent()))
                .map(LogObject::getNumberTask)
                .forEach(number -> map.put(number, map.containsKey(number) ? map.get(number) + 1 : 1));
        return map;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        filterByTime(after, before)
                .filter(log -> isDoneEvent(log.getEvent()))
                .map(LogObject::getNumberTask)
                .forEach(number -> map.put(number, map.containsKey(number) ? map.get(number) + 1 : 1));
        return map;
    }

    /*********************************************************************************************
     *
     * QLQuery methods
     *
     * *******************************************************************************************/

    @Override
    public Set<Object> execute(String query) {
        QueryObject q = createQuery(query);
        if (q != null) {
            switch (q.getQueryType()) {
                case QL_SIMPLE:
                    return executeSimpleQuery(q);
                case QL_MEDIUM:
                    return executeMediumQuery(q);
                case QL_HARD:
                    return executeHardQuery(q);
            }
        }
        return null;
    }

    /**
     * Запросы типа
     * get field
     */
    private Set<Object> executeSimpleQuery(QueryObject q) {
        return filterByField(q.getField1());
    }

    /**
     * Запросы типа
     * get field1 for field2 = "value1"
     */
    private Set<Object> executeMediumQuery(QueryObject q) {
        return filterByField(filterByValue(q.getField2(), q.getValue()), q.getField1());
    }

    /**
     * Запросы типа
     * get field1 for field2 = "value1" and date between "after" and "before"
     */
    private Set<Object> executeHardQuery(QueryObject q) {
        List<LogObject> list = logs.stream()
                .filter(log -> log.getDate().after(q.getAfter()) && log.getDate().before(q.getBefore()))
                .collect(Collectors.toList());
        return filterByField(filterByValue(list, q.getField2(), q.getValue()), q.getField1());
    }

    /**
     * Создаем обект запроса
     */
    private QueryObject createQuery(String string) {
        String field1 = findField1(string);
        String field2 = findField2(string);
        String value = findValue(string);
        Object object = createObject(field2, value);


        Date after = findAfterDate(string);
        Date before = findBeforeDate(string);

        return new QueryObject(field1, field2, object, after, before);
    }

    private boolean correctData(String string) {
        String afterDate = findRegQuery(string, AFTER);
        String beforeDate = findRegQuery(string, BEFORE);

        if (afterDate != null && beforeDate != null) {
            Date after = convertToDate(afterDate);
            Date before = convertToDate(beforeDate);
            return after != null && before != null;
        } else if (beforeDate == null) {
            return false;
        }
        return true;
    }

    /**
     * Опоредиляем обект Value по Field
     */
    private Object createObject(String field, String value) {
        if (field != null && value != null) {
            try {
                switch (field) {
                    case DATE:
                        return convertToDate(value);
                    case EVENT:
                        return Arrays.stream(Event.values())
                                .filter(event -> event.toString().equals(value))
                                .findFirst()
                                .orElse(null);
                    case STATUS:
                        return Arrays.stream(Status.values())
                                .filter(status -> status.toString().equals(value))
                                .findFirst()
                                .orElse(null);
                    default:
                        return value;
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
    * Find in query methods
    *
    *
    *
    *
    */

    private String findField1(String query) {
        return findRegQuery(query, FIELD1);
    }

    private String findField2(String query) {
        return findRegQuery(query, FIELD2);
    }

    private String findValue(String query) {
        return findRegQuery(query, VALUE);
    }

    private Date findAfterDate(String query) {
        return convertToDate(findRegQuery(query, AFTER));
    }

    private Date findBeforeDate(String query) {
        return convertToDate(findRegQuery(query, BEFORE));
    }

    /**
     * ищет запрос по паттерну определенной группы
     */
    private String findRegQuery(String string, String group) {
        return findReg(string, group, QUERY_PATTERN);
    }

    /*
    * Filters methods
    *
    *
    *
    *
    */

    /**
     * Берет поля по параметру с logs
     */
    private Set<Object> filterByField(String field1) {
        switch (field1.toLowerCase()) {
            case IP:
                return getLogsStream().map(LogObject::getIp).collect(Collectors.toSet());
            case USER:
                return getLogsStream().map(LogObject::getUser).collect(Collectors.toSet());
            case DATE:
                return getLogsStream().map(LogObject::getDate).collect(Collectors.toSet());
            case EVENT:
                return getLogsStream().map(LogObject::getEvent).collect(Collectors.toSet());
            case STATUS:
                return getLogsStream().map(LogObject::getStatus).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Берет поля по параметру с определеного logs
     */
    private Set<Object> filterByField(List<LogObject> logs, String field1) {
        if (logs != null) {
            Stream<LogObject> logObjectStream = logs.stream();
            switch (field1.toLowerCase()) {
                case IP:
                    return logObjectStream.map(LogObject::getIp).collect(Collectors.toSet());
                case USER:
                    return logObjectStream.map(LogObject::getUser).collect(Collectors.toSet());
                case DATE:
                    return logObjectStream.map(LogObject::getDate).collect(Collectors.toSet());
                case EVENT:
                    return logObjectStream.map(LogObject::getEvent).collect(Collectors.toSet());
                case STATUS:
                    return logObjectStream.map(LogObject::getStatus).collect(Collectors.toSet());
            }
        }
        return new HashSet<>();
    }

    /**
     * Берет поля по параметру и значению с logs
     */
    private List<LogObject> filterByValue(String field, Object value) {
        switch (field.toLowerCase()) {
            case IP:
                return getLogsStream().filter(log -> log.getIp().equals(value.toString())).collect(Collectors.toList());
            case USER:
                return getLogsStream().filter(log -> log.getUser().equals(value.toString())).collect(Collectors.toList());
            case DATE:
                return getLogsStream().filter(log -> log.getDate().getTime() == ((Date) value).getTime()).collect(Collectors.toList());
            case EVENT:
                return getLogsStream().filter(log -> log.getEvent() == value).collect(Collectors.toList());
            case STATUS:
                return getLogsStream().filter(log -> log.getStatus() == value).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Берет поля по параметру и значению с определеного logs
     */
    private List<LogObject> filterByValue(List<LogObject> logs, String field, Object value) {
        if (logs != null) {
            Stream<LogObject> logObjectStream = logs.stream();
            switch (field.toLowerCase()) {
                case IP:
                    return logObjectStream.filter(log -> log.getIp().equals(value.toString())).collect(Collectors.toList());
                case USER:
                    return logObjectStream.filter(log -> isLogOfUser(log, value.toString())).collect(Collectors.toList());
                case DATE:
                    return logObjectStream.filter(log -> log.getDate().getTime() == ((Date) value).getTime()).collect(Collectors.toList());
                case EVENT:
                    return logObjectStream.filter(log -> log.getEvent() == value).collect(Collectors.toList());
                case STATUS:
                    return logObjectStream.filter(log -> log.getStatus() == value).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}