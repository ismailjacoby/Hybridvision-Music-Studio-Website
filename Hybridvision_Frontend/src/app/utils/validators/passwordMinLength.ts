import {AbstractControl, ValidatorFn} from "@angular/forms";

export function passwordMinLength(): ValidatorFn{
  return (control:AbstractControl)=>{
    const password = control.value;
    if(password && password.length<6){
      return {passwordMinLength: true};
    } else{
      return null;
    }
  }
}
