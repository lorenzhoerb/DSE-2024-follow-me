import { APP_NAME } from '@/assets/config/constants'
import { VehicleType } from '@/types'

export function toPageTitle(subTitle: string | null | unknown): string {
  return subTitle ? `${subTitle} • ${APP_NAME}` : APP_NAME
}

export function getVehicleTypeLabel(type: VehicleType): string {
  switch (type) {
    case VehicleType.LV:
      return 'Leading Vehicle'
    case VehicleType.FV:
      return 'Follow Vehicle'
    default:
      return 'Unknown'
  }
}
