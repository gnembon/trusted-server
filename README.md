# trusted-server
Tiny server mod for Minecraft 1.14.4 that removes extra server checks that would normally prevent certain invalid actions by hacked clients, 
but often causes inability for players on laggy servers and with weak connections to do basic tasks,
like flying, riding vehicles or mining blocks.

## Mining blocks
 - When server receives `START MINING BLOCK` packet and server thinks player is off the ground and 
 could have instamined the block if the player was on the ground, it actually mines the block. If the client
 was actually off the ground, it would send `FINISH MINING BLOCK` packet later, which will be ignored. The change is
 not applied the server thinks the client is flying elytra, or climbing ladders.
 - Server always honors clients `FINISH MINING BLOCK` messages, even if according to the server it should take the client 
 longer to do so. This prevents from reappearing blocks. Server assumes the client calculated block mining speed fairly.
 - Player mining block reach has been extended from 6 blocks to 32 blocks. WHen the server is lagging its easy for player 
 position on the server be inconsistent with what client thinks, causing blocks to fail to mine and reappear.
 
## Player movement
 - players will not be kicked off the server for floating mid air for more than 3 seconds, as well as floating 
 in a vehicle mid air. This should help players with low ping that cannot update their position that often with the server
 or when the server is rubber-banding.
 - Server will not rubber-band players that according to the server are moving too fast (either on their own, or
 on client-controlled vehicles, like boats and horses). Server relies on client 
 speed and position calculations to be correct (e.g. while the server is having low tps)
 - Likewise, server will not stop players mid air for flying too fast.
 - When received `DEPLOY ELYTRA` message, server will ignore if according to the server status player is stationary, or
 not falling down. Server relies on clients themselves to make sure these two conditions are met. The other server checks,
 i.e. player is not already elytra flying, and player is not in water are still performed server side. Clients while flying 
 would still convince the server that they want to use a rocket to propel themselves - that part of the elytra protocol is
 not changed.
 
## Dismounting of vehicles
 - when player dismounts of a vehicle, it will receive an extra packet with vehicle positions. This resolves SOME
 issues with boats or horses to disappear for the client after dismounting of such vehicle. The core issue which is
 wrong client position, which comes in later ticks is not solved yet, but we are working on it.
 - If a player was riding a mob (e.g. horse), dismounting would reset that mob's navigation. This should prevent horses
 wander into oblivion after stopped being ridden.

 
