/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Redmart;

/**
 *
 * @author pc
 */
public class MountainArea {
    public int elevation;
    public int longestAndSteepestPathLength;
    public int lowestAreaElevationInThePath;
    public MountainArea nextMountainAreaInThePath;
    public int rowPosition;
    public int colPosition;
   
    public MountainArea(int elevation,int rowPosition,int colPosition)
    {
        this.elevation=elevation;
        this.rowPosition=rowPosition;
        this.colPosition=colPosition;
    }
    public MountainArea()
    {
        
    }
}
