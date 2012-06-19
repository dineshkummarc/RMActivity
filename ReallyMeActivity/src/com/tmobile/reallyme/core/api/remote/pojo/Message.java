package com.tmobile.reallyme.core.api.remote.pojo;

import java.util.Date;

import com.tmobile.reallyme.application.enums.MessageTypeEnum;


public class Message extends BaseData{
    public String recepient_id;
    public String sender_id;
    public Boolean is_read;
    public String direction;
    public MessageTypeEnum type;
    public String time;
    public String content;

    public Message(String recepient_id, String sender_id, Boolean is_read, String direction, MessageTypeEnum type, String time) {
        this.recepient_id = recepient_id;
        this.sender_id = sender_id;
        this.is_read = is_read;
        this.direction = direction;
        this.type = type;
        this.time = time;
    }

    public String toString() {
        StringBuilder h = new StringBuilder();
        h.append("<message type=\"").append(type.getId()).append("\" time=\"").append(time).append("\" recepient_id=\"");
        h.append(recepient_id).append("\" sender_id=\"").append(sender_id).append("\" is_read=\"").append(is_read);
        h.append("\" id=\"").append(id).append("\" direction=\"").append(direction).append("\"");
        if (lastUpdate != null) {
            h.append(" lastUpdate=\"").append(new Date());
            //TODO: Kolesnil Aleksey
//            h.append(IsoDate.dateToString(lastUpdate, 3));
            h.append("\">\n");
        } else {
            h.append("{h}>\n");
        }
          if (content != null ) {
              h.append("<content><![CDATA[").append(content).append("]]></content>\n");
          }
          h.append("</message>\n");

        return h.toString();
    }
}
