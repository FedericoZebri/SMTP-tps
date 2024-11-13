import java.io.Serializable;

public class Messaggio implements Serializable {
    private String from;
    private String to;
    private String header;
    private String body;

    public Messaggio(String from, String to, String header, String body) {
        this.from = from;
        this.to = to;
        this.header = header;
        this.body = body;
    }

    public Messaggio() {
        this.from = null;
        this.to = null;
        this.header = null;
        this.body = null;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return  "Message from:" + from + '\n' +
                "to:" + to + '\n' +
                "Oggetto: " + header + '\n' +
                "Contenuto:" + '\n' + body + '\n';
    }
}
