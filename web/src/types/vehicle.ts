export interface BasicVehicleInfo {
  vin: string
  model: string
  manufacturer: string
  type: VehicleType
  isInFollowMeMode: boolean
}

export enum VehicleType {
  LV = 'LV',
  FV = 'FV'
}
