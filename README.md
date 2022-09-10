# statosu-discord
statosu is a Discord bot that helps you to track your progress in osu!! classic mode.
## How to use statosu?
To track your statistic with statosu you have to do three simple steps:
1. add bot to your Discord channel
2. set your username in osu!
3. set when you want to get statistic updates
### How to do that?
statosu has these three simple slash commands:
- about - help
- username **your-nickname** - sets your osu! nickname
- date **period**[daily, weekly, monthly] [number-of-month(0-28), day-of-week] **update_hour**(0-23) - sets update period and time.
## Example of the update message
![](message_example.png)

## How to deploy
If you want to deploy statosu you have to create .env file with such parameters: 
- BOT_TOKEN - token of your Discord bot
- CLIENT_ID - id of your osu! client
- CLIENT_SECRET - osu! client's secret 
After creating file run: `docker compose up -d`
