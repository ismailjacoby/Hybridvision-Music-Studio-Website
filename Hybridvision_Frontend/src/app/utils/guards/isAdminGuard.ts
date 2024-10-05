import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth.service";

export const isAdminGuard: CanActivateFn = (route, state)=>{
  const _authService = inject(AuthService);
  const _router = inject(Router);

  if(_authService.isAdmin()){
    return true;
  } else {
    _router.navigate(['/home']);
    return false;
  }
}
