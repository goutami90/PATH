/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Redmart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.HTML;

/**
 *
 * @author pc
 */
public class SkiingInSg {

    private static MountainArea[][] mountainMap=null;
    private static MountainArea skiStartArea=new MountainArea();
    /**
     * @param args the command line arguments
     */
    
    private static MountainArea getLongestAndSteepestArea(MountainArea a1,MountainArea a2)
    {
        if(a1.longestAndSteepestPathLength>a2.longestAndSteepestPathLength)
            return a1;
        else if((a1.longestAndSteepestPathLength==a2.longestAndSteepestPathLength) && ((a1.elevation-a1.lowestAreaElevationInThePath)>(a2.elevation-a2.lowestAreaElevationInThePath)))
            return a1;
        return a2;
    }
    
    private static void printLongestAndSteepestPath()
    {
        System.out.println("Length: "+skiStartArea.longestAndSteepestPathLength+", drop: "+(skiStartArea.elevation-skiStartArea.lowestAreaElevationInThePath));
        MountainArea nextArea=skiStartArea;
        System.out.println("Path");
        while(nextArea!=null)
        {
            System.out.println("-->"+nextArea.elevation);
            nextArea=nextArea.nextMountainAreaInThePath;
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        setMountainMap("C:\\Users\\pc\\Desktop\\map.txt", 1500);
        if(mountainMap!=null)
        {
            for(int i=1;i<=mountainMap.length-2;i++)
            {
                for(int j=1;j<=mountainMap[0].length-2;j++)
                {
                    MountainArea area= findLongestAndSteepestPath(i, j);
                    skiStartArea=getLongestAndSteepestArea(skiStartArea, area);
                }
            }
            printLongestAndSteepestPath();
        }
    }
    
    private static ArrayList<MountainArea> getLowerAreas(int rowInd,int colInd)
    {
        ArrayList<MountainArea> areas=new ArrayList<>();
        if(mountainMap[rowInd][colInd].elevation>mountainMap[rowInd+1][colInd].elevation)
            areas.add(mountainMap[rowInd+1][colInd]);
        if(mountainMap[rowInd][colInd].elevation>mountainMap[rowInd-1][colInd].elevation)
            areas.add(mountainMap[rowInd-1][colInd]);
        if(mountainMap[rowInd][colInd].elevation>mountainMap[rowInd][colInd+1].elevation)
            areas.add(mountainMap[rowInd][colInd+1]);
        if(mountainMap[rowInd][colInd].elevation>mountainMap[rowInd][colInd-1].elevation)
            areas.add(mountainMap[rowInd][colInd-1]);
        return areas;
    }
    
    private static MountainArea findLongestAndSteepestPath(int rowInd,int colInd)
    {
        MountainArea currentArea=mountainMap[rowInd][colInd];
        MountainArea nextAreaInThePath;
        if(currentArea.longestAndSteepestPathLength!=0)
        {
            return currentArea;
        }
        else
        {
            ArrayList<MountainArea> lowerAreas= getLowerAreas(rowInd, colInd);
            if(lowerAreas.isEmpty())
            {
                currentArea.longestAndSteepestPathLength=1;
                currentArea.lowestAreaElevationInThePath=currentArea.elevation;
                currentArea.nextMountainAreaInThePath=null;
            }
            else
            {
                nextAreaInThePath=findLongestAndSteepestPath(lowerAreas.get(0).rowPosition,lowerAreas.get(0).colPosition);
                for(int i=1;i<lowerAreas.size();i++)
                {
                    MountainArea area=findLongestAndSteepestPath(lowerAreas.get(i).rowPosition, lowerAreas.get(i).colPosition);
                    if((area.longestAndSteepestPathLength>nextAreaInThePath.longestAndSteepestPathLength)||((area.longestAndSteepestPathLength==nextAreaInThePath.longestAndSteepestPathLength) && (area.lowestAreaElevationInThePath<nextAreaInThePath.lowestAreaElevationInThePath)))
                         nextAreaInThePath=area;
                }
                currentArea.longestAndSteepestPathLength=nextAreaInThePath.longestAndSteepestPathLength+1;
                currentArea.lowestAreaElevationInThePath=nextAreaInThePath.lowestAreaElevationInThePath;
                currentArea.nextMountainAreaInThePath=nextAreaInThePath;
            }
            return currentArea;
        }
        
    }
    
    private static void setMountainMap(String filePath,int maxElevation)
    {
          try {
            File f=new File(filePath);      
            Scanner sc=new Scanner(f);
            int rows=sc.nextInt()+2;
            int cols=sc.nextInt()+2;
            mountainMap=new MountainArea[rows][cols];
            setPaddingElementsForMountainMap(rows, cols, maxElevation);
            for(int i=1;i<=rows-2;i++)
            {
                for(int j=1;j<=cols-2;j++)
                {
                    mountainMap[i][j]=new MountainArea(sc.nextInt(),i,j);
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SkiingInSg.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The map file could not be found in the specified location.");
            mountainMap=null;
        }
        catch(Exception ex)
        {
            Logger.getLogger(SkiingInSg.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("An error occured while reading the map file.");
            mountainMap=null;
        }
        
    }
    private static void setPaddingElementsForMountainMap(int rows,int cols,int maxElevation)
    {
        
            for(int i=0;i<rows;i++)
            {
                mountainMap[i][0]=new MountainArea(maxElevation+1,i,0);
                mountainMap[i][cols-1]=new MountainArea(maxElevation+1,i,cols-1);
            }
            for(int i=0;i<cols;i++)
            {
                mountainMap[0][i]=new MountainArea(maxElevation+1,0,i);
                mountainMap[rows-1][i]=new MountainArea(maxElevation+1,rows-1,i);
            }
    }
}
