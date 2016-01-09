import names
import csv
import random

random.seed(0)

OUTPUT_PATH = '/home/cbarnes/code/ultimanager/db/players.csv'
PLAYERS_PER_TEAM = 7
MEAN_HEIGHT = 176
STD_HEIGHT = 7.62
AGE_RANGE = (18, 26)
ABILITY_RANGE = (1, 10)

TEAMS = [
    'Black Dynamite',
    'Pitch Disc',
    'Bimble with Nimble',
    'Golden Snatch'
]

headers = ['playerName', 'age', 'team', 'height', 'ability']

with open(OUTPUT_PATH, 'w') as f:
    writer = csv.writer(f)
    writer.writerow(headers)
    for team in TEAMS:
        for _ in range(PLAYERS_PER_TEAM):
            writer.writerow([
                    names.get_full_name(gender='male'),
                    random.randint(*AGE_RANGE),
                    team,
                    round(random.gauss(MEAN_HEIGHT, STD_HEIGHT)),
                    random.randint(*ABILITY_RANGE)
            ])
