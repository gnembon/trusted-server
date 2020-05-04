# trusted-server
Tiny server mod for Minecraft 1.15.2 that removes extra server checks that would normally prevent certain invalid actions by hacked clients, 
but often causes inability for players on laggy servers and with weak connections to do basic tasks,
like flying, riding vehicles or mining blocks.

## Mining blocks
 - Player mining block reach has been extended from 6 blocks to 32 blocks. When the server is lagging its easy for player 
 position on the server be inconsistent with what client thinks, causing blocks to fail to mine and reappear.
 
## Player movement
 - players will not be kicked off the server for floating mid air for more than 3 seconds, as well as floating 
 in a vehicle mid air. This should help players with low ping that cannot update their position that often with the server
 or when the server is rubber-banding.
 - Server will not rubber-band players that according to the server are moving too fast (either on their own, or
 on client-controlled vehicles, like boats and horses). Server relies on client 
 speed and position calculations to be correct (e.g. while the server is having low tps)
 - Likewise, server will not stop players mid air for flying too fast.
 

 
