export interface BasicVehicleInfo {
  vin: string
  model: string
  manufacturer: string
  manufacturerCode: string
  type: VehicleType
}

export enum VehicleType {
  LV = 'LV',
  FV = 'FV'
}

export interface VehicleData {
  vin: string
  location?: {
    longitude: number
    latitude: number
  }
  velocity: number
  lane: number
  targetControl?: {
    targetVelocity: number
    targetLane: number
  }
  timestamp: Date
}

export interface VehicleStatus {
  vin: string
  followMeModeActive: boolean
  pairedVin: string | null
  targetControl?: {
    targetVelocity: number
    targetLane: number
  }
}

export interface VehicleInfo {
  vin: string
  info?: BasicVehicleInfo
  data?: VehicleData
  status?: VehicleStatus
}

export interface VehicleCardEntry {
  vin: string
  model: string
  manufacturer: string
  manufacturerCode: string
  type: VehicleType
  isInFollowMeMode: boolean
  data: {
    velocity: number,
    lane: number
  } | null
}

export interface VehicleTableEntry {
  vin: string
  model: string
  manufacturer: string
  manufacturerCode: string
  type: VehicleType
  isInFollowMeMode: boolean
  pairedWith: string | null | undefined
}
