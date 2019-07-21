# GenericTVProgramTracker
generic program tracker using csv database


In order to import MAL into the database:
1. Go to edit profile > list and set the list to only show episodes 
2. Copy the watching table into a text editor and find "edit - more\n" and replace with "", do the same for "airing" and "+"
3. Copy that into calc/exel, and make sure it is seperated by tabs and "/"
4. Check if any of the cells got pushed around by special characters in titles and clean those that did
5. The entries should be in the order "title", "current episode", "total episodes", Airing
6. Mark entries that are airing with "True" in the last collumn, and those that are not with "False"
7. Save as a CSV file, Field deliminated by "," and String deliminated by """
8. Save CSV file in same location as the program root, calling the file "watching.csv"
9. Repeat for Completed, making sure that the collumn names are properly followed as MAL displays a complete anime as 12 instead of 12/12
10. Save as "complete.csv"
