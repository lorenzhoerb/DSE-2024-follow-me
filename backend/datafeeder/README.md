# Datafeeder

Simulates the FollowMe scenario. The simulation the vehicles `MEC-X1-1000` (FV) and `MEC-X1-1001` (LV).

## Preconditions

Both vehicles must be in the inventory

## Run

When run, the vehicles perform maneuvers, end goes through following states:

1. `ENGAGEMENT`: follow vehicles approaches to lead vehicle, so they can be in the minimum distance for the follow me mode.
2. `SPEED_INC_1`: Leading Vehicles increases its velocity by 20km/h; Follow Vehicle adapts changes (based on control service)
3. `SPEED_DEC_1`: Leading Vehicle decreases its velocity by 20km/h; Follow Vehicle adapts changes (based on control service)
4. `LANE_CHANGE_1`: Leading Vehicle changes its lane; Follow Vehicle adapts changes (based on control service)
5. `LANE_CHANGE_2`: Leading Vehicle changes its lane; Follow Vehicle adapts changes (based on control service)
6. `SPEED_INC_2`: Leading Vehicles increases its velocity by 20km/h; Follow Vehicle does not match targets: Control service should disengage.

## Deploy

1. Run `mvn package`
2. Docker image: `docker build -t dse/datafeeder:latest`


#### Responsibility
Lorenz

