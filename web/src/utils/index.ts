import { APP_NAME } from "@/assets/config/constants";

export function toPageTitle(subTitle: string | null | unknown): string {
  return subTitle ? `${subTitle} • ${APP_NAME}` : APP_NAME;
}
