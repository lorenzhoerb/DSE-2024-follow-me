import { fetchVehicleData } from './BeachcombService'
import { fetchVehicleStatus } from './ControlService'
import { fetchInventory } from './InventoryService'
import type { VehicleInfo } from '@/types'

export const fetchAll = async (): Promise<VehicleInfo[]> => {
  let vehicleData;
  let vehicleStatus;
  let inventory;

  try {
    vehicleData = await fetchVehicleData();
  } catch (error) {
    console.error('Error fetching vehicle data:', error);
  }

  try {
    vehicleStatus = await fetchVehicleStatus();
  } catch (error) {
    console.error('Error fetching vehicle status:', error);
  }

  try {
    inventory = await fetchInventory();
  } catch (error) {
    console.error('Error fetching inventory:', error);
  }

  const vehicleInfoMap = new Map<string, VehicleInfo>();

  if (vehicleData) {
    vehicleData.forEach((data) => {
      vehicleInfoMap.set(data.vin, {
        vin: data.vin,
        data,
        ...vehicleInfoMap.get(data.vin)
      });
    });
  }

  if (vehicleStatus) {
    vehicleStatus.forEach((status) => {
      vehicleInfoMap.set(status.vin, {
        vin: status.vin,
        status,
        ...vehicleInfoMap.get(status.vin)
      });
    });
  }

  if (inventory) {
    inventory.forEach((vehicle) => {
      vehicleInfoMap.set(vehicle.vin, {
        vin: vehicle.vin,
        info: vehicle,
        ...vehicleInfoMap.get(vehicle.vin)
      });
    });
  }
  return Array.from(vehicleInfoMap.values())
}
