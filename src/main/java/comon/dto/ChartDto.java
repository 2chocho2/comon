package comon.dto;

import lombok.Data;

@Data
public class ChartDto {

   private String registDt;
   private int monthlyCount;
   private int countAllUser;
   private int countAllDev;
   private int imageIdx;
   private String downloadDate;
   private int downloadCount;
   private String imageName;
   private int totalUser;
   private int totalDownloadCount;
   private int totalOpenApp;

}