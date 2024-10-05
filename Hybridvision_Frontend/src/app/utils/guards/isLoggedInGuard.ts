import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth.service";

export const isLoggedInGuard: CanActivateFn = (route, state) =>{
  const _authService = inject(AuthService);
  const _route = inject(Router)

  if(_authService.isLoggedIn()){
    return true;
  } else {
    _route.navigate(['account/login']);
    return false;
  }
}
