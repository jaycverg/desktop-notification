package com.jaycverg.desktop.notification.model;

import java.util.Date;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class Holiday
{
    private static final long serialVersionUID = 1L;

    private Date date;
    private String name;
    private String description;

    /**
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
}
