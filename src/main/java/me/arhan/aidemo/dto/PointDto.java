package me.arhan.aidemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.arhan.aidemo.math.Point;

/**
 * Data Transfer Object for Point.
 * This class is used for transferring point data between the client and the server.
 */
public class PointDto {
    
    @JsonProperty("x")
    private int x;
    
    @JsonProperty("y")
    private int y;
    
    /**
     * Default constructor for deserialization.
     */
    public PointDto() {
    }
    
    /**
     * Constructs a new PointDto with the specified coordinates.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public PointDto(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Constructs a new PointDto from a Point object.
     *
     * @param point The Point object to convert
     */
    public PointDto(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }
    
    /**
     * Converts this DTO to a Point object.
     *
     * @return A new Point object with the same coordinates as this DTO
     */
    public Point toPoint() {
        return new Point(x, y);
    }
    
    /**
     * Gets the x-coordinate of this point.
     *
     * @return The x-coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * Sets the x-coordinate of this point.
     *
     * @param x The new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * Gets the y-coordinate of this point.
     *
     * @return The y-coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Sets the y-coordinate of this point.
     *
     * @param y The new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
} 