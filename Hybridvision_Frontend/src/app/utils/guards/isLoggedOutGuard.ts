import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth.service";

export const isLoggedOutGuard: CanActivateFn = (route, state) => {
  const _authService = inject(AuthService);
  const _router = inject(Router)

  if (_authService.isLoggedIn()){
    _router.navigate(['home'])
    return false;
  } else {
    return true;
  }


}
