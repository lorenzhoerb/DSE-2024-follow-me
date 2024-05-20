export interface BasicVehicleInfo {
  vin: string
  model: string
  manufacturer: string
  manufacturerCode: string
  type: VehicleType
  isInFollowMeMode: boolean
}

export enum VehicleType {
  LV = 'LV',
  FV = 'FV'
}
