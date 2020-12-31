# Redeem/Reclaim Plugin

Redeem/Reclaim Commands
Compatible Versions: 1.8.X - 1.13.X
Developer: Jason Wang

## Overview
In previous servers, I have seen a custom command given: /reclaim, which is normally for a server opening or server reset. The purpose of that command was to give a player the ability to use the command one time and obtain items or other things specific to their rank or group. Because I have not found anyone else selling a plugin with these features I thought it would be nice to make one myself. The command is /reclaim because some plugins may already use the /redeem command.

## Features
The plugin consists of two configurations, one for setting up groups and custom permissions, and the other is for storing the UUIDs of players. Inside the first configuration, you are able to add as many commands to a group that you would like the console to run when it is called by a player. After the player uses the /reclaim command on that group they will be added to the list of players in the other config and unable to access the groups commands again (unless an admin removes them from the list). The config is super easy to use and the commands are very user-friendly.
​
Available In-Game Commands:
/reclaim
- Lists out the commands in the game that the user can use. If the player does not have permission reclaim.admin they can only see the command /reclaim <group>.
/reclaim <group>
- The player can use this to reclaim the one-time-use set commands for their group.
/reclaim reload
- Reloads both configurations.
/reclaim add <group>
- Adds a new group to the redeemConfig.yml with custom permission and set up area.
/reclaim addCommand <group> <command>
- Admin can add a new command to a specific group.
/reclaim clearPlayers
- Clear the userList.yml of blacklisted players.
/reclaim addPlayer <name>
- Add a player to the blacklist of players for using /reclaim.
/reclaim removePlayer <name>
- Remove a player fro the blacklist of players.
/reclaim viewGroup <group name>
- Use to view the group and the commands that it currently will execute when called.
/reclaim listGroups
- Lists all the groups currently added

Permissions:
reclaim.admin
reclaim.<group name>
